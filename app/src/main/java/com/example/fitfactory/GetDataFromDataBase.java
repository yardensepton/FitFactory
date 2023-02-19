package com.example.fitfactory;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import com.example.fitfactory.Model.GymClass;
import com.example.fitfactory.Model.Trainer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class GetDataFromDataBase {
    private static final FirebaseDatabase db = FirebaseDatabase.getInstance();
    private static final DatabaseReference usersPath = db.getReference(Finals.users);
    private static final DatabaseReference gymClassesPath = db.getReference(Finals.gym_Classes);
    private static final DatabaseReference trainersPath = db.getReference(Finals.trainers);
    private static final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final ArrayList<GymClass> allGymClasses = new ArrayList<>();

    public GetDataFromDataBase() {

    }

//    public ArrayList<GymClass> getSpecificUserClasses(User user) {
//        ArrayList<GymClass> specificUserClasses = new ArrayList<>();
//        usersPath.child(user.getUid()).child(Finals.gymClasses).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if (task.isSuccessful()) {
//                    for (DataSnapshot ds : task.getResult().getChildren()) {
//                        GymClass gymClass = ds.getValue(GymClass.class);
//                        assert gymClass != null;
//                        specificUserClasses.add(gymClass);
//                        Log.d("in get class - class teacher", "" + gymClass.getTeacherName() + " " + user.getName());
//                    }
//                } else {
//                    MySignal.getInstance().toast("Can't find classes");
//                }
//
//
//            }
//        });
//        return specificUserClasses;
//    }




    public static HashMap<String, GymClass> getTrainersFromFB(String trainer) {
        HashMap<String, GymClass> gymClasses = new HashMap<>();
        trainersPath.child(trainer).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    for (DataSnapshot ds : task.getResult().getChildren()) {
                        GymClass gymClass = ds.getValue(GymClass.class);
                        assert gymClass != null;
                        gymClasses.put(gymClass.getClassUUid(), gymClass);
                    }
                } else {
                    MySignal.getInstance().toast("Can't find classes");
                }


            }
        });
        return gymClasses;

    }


    public static void getTrainerData(String trainer) {
        trainersPath.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(Finals.trainers).child(trainer).exists()) {
                    trainersPath.child(Finals.trainers).child(trainer).get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Trainer thisTrainer = task.getResult().getValue(Trainer.class);
                            assert thisTrainer != null;
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
