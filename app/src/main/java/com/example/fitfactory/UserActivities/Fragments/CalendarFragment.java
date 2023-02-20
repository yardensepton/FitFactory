package com.example.fitfactory.UserActivities.Fragments;

import static com.example.fitfactory.UserActivities.CalendarUtils.daysInWeekArray;
import static com.example.fitfactory.UserActivities.CalendarUtils.monthYearFromDate;
import static com.example.fitfactory.UserActivities.CalendarUtils.selectedDate;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitfactory.Finals;
import com.example.fitfactory.Model.GymClass;
import com.example.fitfactory.Model.User;
import com.example.fitfactory.MySignal;
import com.example.fitfactory.R;
import com.example.fitfactory.UserActivities.RV_adapter_gymClasses;
import com.example.fitfactory.UserActivities.CalendarAdapter;
import com.example.fitfactory.UserActivities.CalendarUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;


public class CalendarFragment extends Fragment implements CalendarAdapter.OnItemListener, RV_adapter_gymClasses.OnItemClickListener {
    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    private DatabaseReference databaseReference;
    private TextView calendar_name;
    private ArrayList<GymClass> gymClasses;
    private RV_adapter_gymClasses adapter_classes;
    private ArrayList<GymClass> chosenDayGymClasses;
    private RecyclerView recycle_gymClassesInTheDate;
    private ImageButton calendar_BTN_lastWeek;
    private User user;
    private ImageButton calendar_BTN_nextWeek;
    private RecyclerView recycle_datesOfWeek;


    public CalendarFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference();
        chosenDayGymClasses = new ArrayList<>();
        gymClasses = new ArrayList<>();
        getUserData();
        getClassesOfUserFromDB();
        findViews(view);
        CalendarUtils.selectedDate = LocalDate.now();
        setWeekView();
        getClassesInSpecificDateFromDB(LocalDate.now());
        previousWeekAction();
        nextWeekAction();
        return view;


    }


    private void findViews(View view) {
        recycle_gymClassesInTheDate = view.findViewById(R.id.recycle_gymClassesInTheDate);
        recycle_datesOfWeek = view.findViewById(R.id.recycle_datesOfWeek);
        calendar_name = view.findViewById(R.id.calendar_name);
        calendar_BTN_lastWeek = view.findViewById(R.id.calendar_BTN_lastWeek);
        calendar_BTN_nextWeek = view.findViewById(R.id.calendar_BTN_nextWeek);
    }


    private void getClassesOfUserFromDB() {

        db.getReference().child(Finals.gym_Classes).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        if (ds.hasChild(Finals.signedUsers)) {
                            Log.d("yayyyyyyyyyyyyyyyyy", "");
                            Object snapshotValue = ds.getValue();
                            assert snapshotValue != null;
                            GymClass gymClass = ds.getValue(GymClass.class);
                            assert gymClass != null;
                            for (String us : gymClass.getSignedUsers()) {
                                Log.d("yayyyyyyyyyyyyyyyyy 2", "");
                                if (us.equals(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())) {
                                    gymClasses.add(gymClass);
                                    Log.d("yayyyyyyyyyyyyyyyyy 3", "");
                                }

                            }

                        }

                    }
                } else {
                    Log.d("get classes of user in home user fra", "");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    private void setWeekView() {
        calendar_name.setText(monthYearFromDate(selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInWeekArray(CalendarUtils.selectedDate);
        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 7);
        recycle_datesOfWeek.setLayoutManager(layoutManager);
        recycle_datesOfWeek.setAdapter(calendarAdapter);

    }


    public void previousWeekAction() {
        calendar_BTN_lastWeek.setOnClickListener(v -> {
            CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
            setWeekView();
        });

    }


    public void nextWeekAction() {
        calendar_BTN_nextWeek.setOnClickListener(v -> {
            CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
            setWeekView();

        });
    }


    private void getClassesInSpecificDateFromDB(LocalDate localDate) {
        db.getReference().child(Finals.gym_Classes).orderByChild(Finals.date).equalTo(localDate.toString()).
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            chosenDayGymClasses.clear();
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                GymClass gymClass = ds.getValue(GymClass.class);
                                assert gymClass != null;
                                chosenDayGymClasses.add(gymClass);
                            }
                            setGymClassAdapter();
                        } else {
                            recycle_gymClassesInTheDate.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
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


    private void setGymClassAdapter() {
        recycle_gymClassesInTheDate.setVisibility(View.VISIBLE);
        adapter_classes = new RV_adapter_gymClasses(getContext(), chosenDayGymClasses);
        adapter_classes.setOnItemClickListener(this);
        recycle_gymClassesInTheDate.setLayoutManager(new LinearLayoutManager(getContext()));
        recycle_gymClassesInTheDate.setAdapter(adapter_classes);
    }

    @Override
    public void onItemClick(int position, LocalDate date) {
        CalendarUtils.selectedDate = date;
        setWeekView();
        getClassesInSpecificDateFromDB(date);
    }

    @Override
    public void onItemClick(int position) {
        getClassesOfUserFromDB();
        Log.d("classes size", "" + gymClasses.size());
        Log.d("classes pos", "" + adapter_classes.getItem(position));
        int res = user.reserveClass(adapter_classes.getItem(position), gymClasses);
        if (res==0) {
            MySignal.getInstance().toast(Finals.occupied);
        } if (res==-1){
            MySignal.getInstance().toast(Finals.full);
        }if (res ==-2){
            MySignal.getInstance().toast(Finals.closed);
        } else if(res == 1){
            MySignal.getInstance().toast(Finals.added);
            adapter_classes.getItem(position);
        }
    }
}