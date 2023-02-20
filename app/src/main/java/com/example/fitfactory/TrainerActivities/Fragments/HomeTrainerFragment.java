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
import com.example.fitfactory.GetDataFromDataBase;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class HomeTrainerFragment extends Fragment{
    private RV_adapter_TrainerGymClasses adapter_trainer;
    private RecyclerView list_of_classes;
    private TextView homeTrainer_TXT_name;
    private GetDataFromDataBase dataFromDataBase;
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
        dataFromDataBase = new GetDataFromDataBase();
        gymClasses = new ArrayList<>();
        findViews(view);
        getTrainerData();
        getAllTrainerClassesFromDB();
        signOutClick();

//        getData();
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

    private String greetTrainer() {
        Calendar rightNow = Calendar.getInstance();
        int now = rightNow.get(Calendar.HOUR_OF_DAY);
        if (now > 5 && now < 12) {
            return "Good Morning ";
        } else if (now > 12 && now < 18) {
            return "Good Afternoon";
        } else {
            return "Good Night";
        }
    }

    private void getAllTrainerClassesFromDB() {
        db.getReference().child(Finals.gym_Classes).orderByChild("trainerId").equalTo(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                GymClass gymClass = ds.getValue(GymClass.class);
                                assert gymClass != null;
                                gymClasses.add(gymClass);
                                Log.d("get classes of user in home user fra working", "" + gymClass.getTeacherName());
                            }
                            setGymClassAdapter();
                        } else {
                            Log.d("get classes of user in home user fra", "");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }




    private void setGymClassAdapter() {
        adapter_trainer = new RV_adapter_TrainerGymClasses(getContext(), showFutureClasses(gymClasses));
        list_of_classes.setLayoutManager(new LinearLayoutManager(getContext()));
        list_of_classes.setAdapter(adapter_trainer);
    }


    public ArrayList<GymClass> showFutureClasses(ArrayList<GymClass> gymClasses) {
        Calendar rightNow = Calendar.getInstance();
        int hour = rightNow.get(Calendar.HOUR_OF_DAY);
        ArrayList<GymClass> relevantClasses = new ArrayList<>();
        for (GymClass gymClass : gymClasses) {
            LocalDate classDate = LocalDate.parse(gymClass.getDate());
            if (classDate.isAfter(LocalDate.now()) || classDate.isEqual(LocalDate.now()) && gymClass.getStartHour()>= hour) {
                relevantClasses.add(gymClass);
            }
        }
        return relevantClasses;
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
                            homeTrainer_TXT_name.setText(greetTrainer() + " " + trainer.getName());
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


    private void removeClassFromDB(GymClass classToRemove) {
        Query gymClassQuery = databaseReference.child(Finals.gym_Classes).orderByChild(Finals.classUUid).equalTo(classToRemove.getClassUUid());

        gymClassQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    data.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("cancel", "onCancelled", databaseError.toException());
            }
        });
    }


}