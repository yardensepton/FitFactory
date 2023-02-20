package com.example.fitfactory.TrainerActivities.Fragments;

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
import com.example.fitfactory.Model.Trainer;
import com.example.fitfactory.MySignal;
import com.example.fitfactory.R;
import com.example.fitfactory.SignOutCallBack;
import com.example.fitfactory.TrainerActivities.RV_adapter_TrainerGymClasses;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class HomeTrainerFragment extends Fragment{
    private RV_adapter_TrainerGymClasses adapter_trainer;
    private RecyclerView list_of_classes;
    private TextView homeTrainer_TXT_name;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase db;
    private Trainer trainer;
    private ArrayList<GymClass> gymClasses;
    private ImageButton homeTrainer_BTN_signOut;
    private SignOutCallBack signOutCallBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_trainer, container, false);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference();
        gymClasses = new ArrayList<>();
        findViews(view);
        getTrainerData();
        getAllTrainerClassesFromDB();
        signOutClick();
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        signOutCallBack = (SignOutCallBack) context;
    }


    private void signOutClick() {
        homeTrainer_BTN_signOut.setOnClickListener(v -> {
            if (signOutCallBack != null) {
                signOutCallBack.signOut();

            }
        });
    }

    public void findViews(View view) {
        homeTrainer_TXT_name = view.findViewById(R.id.homeTrainer_TXT_name);
        list_of_classes = view.findViewById(R.id.list_of_classes);
        homeTrainer_BTN_signOut = view.findViewById(R.id.homeTrainer_BTN_signOut);
    }

    private void getAllTrainerClassesFromDB() {
        db.getReference().child(Finals.gym_Classes).orderByChild(Finals.trainerId).equalTo(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                GymClass gymClass = ds.getValue(GymClass.class);
                                assert gymClass != null;
                                gymClasses.add(gymClass);
                            }
                            setGymClassAdapter();
                        } else {
                            Log.d("error", ""+snapshot.exists());

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }




    private void setGymClassAdapter() {
        adapter_trainer = new RV_adapter_TrainerGymClasses(getContext(), Finals.showFutureClasses(gymClasses));
        list_of_classes.setLayoutManager(new LinearLayoutManager(getContext()));
        list_of_classes.setAdapter(adapter_trainer);
    }


    private void getTrainerData() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(Finals.trainers).child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).exists()) {
                    databaseReference.child(Finals.trainers).child(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            trainer = task.getResult().getValue(Trainer.class);
                            assert trainer != null;
                            homeTrainer_TXT_name.setText(Finals.greetPerson() + " " + trainer.getName());
                            Log.d("uid", trainer.getUid());
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