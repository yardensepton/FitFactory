package com.example.fitfactory.UserActivities.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitfactory.Finals;
import com.example.fitfactory.GetDataFromDataBase;
import com.example.fitfactory.Model.GymClass;
import com.example.fitfactory.Model.User;
import com.example.fitfactory.MySignal;
import com.example.fitfactory.R;
import com.example.fitfactory.RV_adapter_gymClasses;
import com.example.fitfactory.SignOutCallBack;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class HomeUserFragment extends Fragment {
    private RV_adapter_gymClasses adapter_classes;
    private TextView homeUser_TXT_name;
    private RecyclerView list_of_classes;
    private GetDataFromDataBase dataFromDataBase;
    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    private User user;
    private ArrayList<GymClass> gymClasses;
    private AppCompatButton homeUser_BTN_signOut;
    private DatabaseReference databaseReference;
    private SignOutCallBack signOutCallBack;

    public HomeUserFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_user, container, false);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference();
        dataFromDataBase = new GetDataFromDataBase();
        gymClasses = new ArrayList<>();
        findViews(view);
        getUserData();
        getAllUserClassesFromFB();
        signOutClick();
//        getData();
        return view;

    }



    @Override
    public void onAttach(@NonNull Context context)
    {
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


    private String greetUser() {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 0 && timeOfDay < 12){
           return "Good Morning,";
        }else if(timeOfDay >= 12 && timeOfDay < 16){
            return "Good Afternoon,";
        }else if(timeOfDay >= 16 && timeOfDay < 21){
            return "Good Evening,";
        }else{
            return "Good Night,";
        }
    }
    private void getAllUserClassesFromFB() {
        DatabaseReference mDatabase = db.getReference().child(Finals.users).child
                ("" + Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).child(Finals.gymClasses);

        mDatabase.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    for (DataSnapshot ds : task.getResult().getChildren()) {
                        GymClass gymClass = ds.getValue(GymClass.class);
                        assert gymClass != null;
                        gymClasses.add(gymClass);
                    }
                    setGymClassAdapter();
                }
                else {
                    MySignal.getInstance().toast("Can't find classes");
                }


            }
        });


    }


    private void setGymClassAdapter() {
        adapter_classes = new RV_adapter_gymClasses(getContext(), showFutureClasses(gymClasses));
        list_of_classes.setLayoutManager(new LinearLayoutManager(getActivity()));
        list_of_classes.setAdapter(adapter_classes);
    }


    public ArrayList<GymClass> showFutureClasses(ArrayList<GymClass> gymClasses){
        ArrayList<GymClass> relevantClasses = new ArrayList<>();
        for (GymClass gymClass:gymClasses) {
            LocalDate classDate = LocalDate.parse(gymClass.getDate());
            if (classDate.isAfter(LocalDate.now()) || classDate.isEqual(LocalDate.now()) ){
                relevantClasses.add(gymClass);
            }
        }
        return relevantClasses;
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
                            homeUser_TXT_name.setText(greetUser() + " " + user.getName());
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
