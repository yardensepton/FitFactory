package com.example.fitfactory.UserActivities.Fragments;

import static com.example.fitfactory.Finals.showFutureClasses;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitfactory.Finals;
import com.example.fitfactory.Model.GymClass;
import com.example.fitfactory.Model.User;
import com.example.fitfactory.MySignal;
import com.example.fitfactory.R;
import com.example.fitfactory.SignOutCallBack;
import com.example.fitfactory.UserActivities.RV_adapter_gymClasses;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class HomeUserFragment extends Fragment {
    private RV_adapter_gymClasses adapter_classes;
    private TextView homeUser_TXT_name;
    private RecyclerView list_of_classes;
    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    private User user;
    private ArrayList<GymClass> gymClasses;
    private ImageButton homeUser_BTN_signOut;
    private DatabaseReference databaseReference;
    private SignOutCallBack signOutCallBack;

    public HomeUserFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_user, container, false);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference();
        gymClasses = new ArrayList<>();
        findViews(view);
        getUserData();
        getClassesOfUserFromDB();
        signOutClick();
        return view;

    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        signOutCallBack = (SignOutCallBack) context;

    }


    public void findViews(View view) {
        homeUser_TXT_name = view.findViewById(R.id.homeUser_TXT_name);
        list_of_classes = view.findViewById(R.id.list_of_classesUser);
        homeUser_BTN_signOut = view.findViewById(R.id.homeUser_BTN_signOut);

    }


    private void signOutClick() {
        homeUser_BTN_signOut.setOnClickListener(v -> {
            if (signOutCallBack != null) {
                signOutCallBack.signOut();

            }
        });
    }

    private void getClassesOfUserFromDB() {
        db.getReference().child(Finals.gym_Classes).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                if (ds.hasChild(Finals.signedUsers)) {
                                    Object snapshotValue = ds.getValue();
                                    assert snapshotValue != null;
                                    GymClass gymClass = ds.getValue(GymClass.class);
                                    assert gymClass != null;
                                    for (String us:gymClass.getSignedUsers()) {
                                        if (us.equals(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())){
                                            gymClasses.add(gymClass);
                                        }

                                    }

                                }

                            }
                            setGymClassAdapter();
                        } else {
                            Log.d("error",""+ snapshot.exists());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void setGymClassAdapter() {
        adapter_classes = new RV_adapter_gymClasses(getContext(), showFutureClasses(gymClasses));
        list_of_classes.setLayoutManager(new LinearLayoutManager(getActivity()));
        list_of_classes.setAdapter(adapter_classes);
    }


    private void getUserData() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(Finals.users).child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).exists()) {
                    databaseReference.child(Finals.users).child(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            user = task.getResult().getValue(User.class);
                            assert user != null;
                            homeUser_TXT_name.setText(Finals.greetPerson()+ " " + user.getName());
                        } else {
                            MySignal.getInstance().toast(String.valueOf(task.getException()));
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });


    }


}
