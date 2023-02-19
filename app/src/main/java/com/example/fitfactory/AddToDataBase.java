package com.example.fitfactory;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.fitfactory.Model.GymClass;
import com.example.fitfactory.Model.Trainer;
import com.example.fitfactory.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class AddToDataBase {
    private static final FirebaseDatabase db = FirebaseDatabase.getInstance();
    private static final DatabaseReference usersPath = db.getReference(Finals.users);;
    private final DatabaseReference gymClassesPath = db.getReference(Finals.gym_Classes);
    private final DatabaseReference trainersPath = db.getReference(Finals.trainers);;

    public AddToDataBase() {
    }

    public void addUserToDB(User user){
        usersPath.child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).setValue(user);
    }

    public static void updateUserInDB(User user){
        usersPath.child(""+user.getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Log.d("updating user","");
                }
                else {
                    MySignal.getInstance().toast(task.getException().toString());
                }
            }
        });
    }



    public static void updateAllUsersInDB() {
        db.getReference().child(Finals.users).
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                User user = ds.getValue(User.class);
                                assert user != null;
                                updateUserInDB(user);
                            }
                        } else {
                            MySignal.getInstance().toast("problem");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });
    }




    public void updateGymClassInDB(GymClass gymClass){
        gymClassesPath.child(""+gymClass.getClassUUid()).setValue(gymClass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){

                }  else {
                    MySignal.getInstance().toast(task.getException().toString());
                }
            }
        });
    }


//    public void updateGymClassInfoInTrainer(GymClass gymClass){
//        trainersPath.child(""+gymClass.getClassUUid()).setValue(gymClass).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()){
//
//                }  else {
//                    MySignal.getInstance().toast(task.getException().toString());
//                }
//            }
//        });
//    }



//    public void addTrainerToDB(Trainer trainer){
//        trainersPath.child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).setValue(trainer);
//    }

    public void updateTrainerInDB(Trainer trainer){
        trainersPath.child(trainer.getUid()).setValue(trainer).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){

                }  else {
                    MySignal.getInstance().toast(task.getException().toString());
                }
            }
        });
    }

}
