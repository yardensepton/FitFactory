package com.example.fitfactory.TrainerActivities.Fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.fitfactory.Finals;
import com.example.fitfactory.Model.Trainer;
import com.example.fitfactory.MySignal;
import com.example.fitfactory.R;
import com.example.fitfactory.TrainerActivities.HoursAdapter;
import com.example.fitfactory.TrainerActivities.ReplacingGymClassChoice;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.Objects;

public class AddClassFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    private DatabaseReference databaseReference;
    private String hour;
    private ImageButton addClass_PIC_pilates;
    private TextView boxing_name;
    private TextView pilates_name;
    private TextView yoga_name;
    private TextView crossfit_name;
    private TextView dance_name;
    private TextView cycling_name;
    private ImageButton addClass_PIC_yoga;
    private LocalDate currentDate = LocalDate.now();
    private ImageButton addClass_PIC_dance;
    private ImageButton addClass_PIC_cycling;
    private ImageButton addClass_PIC_crossfit;
    private ImageButton addClass_PIC_boxing;
    private ReplacingGymClassChoice setImageResource;
    private TextView addClass_TXT_selectDate;
    private AppCompatButton addClass_BTN_availableHours;
    private int year;
    private int month;
    private AppCompatButton addClass_BTN_addClass;
    private Finals.classNames currentClassName = null;
    private int day;
    private LocalDate pickedDate;
    private Trainer trainer;
    private HoursAdapter hoursAdapter;
    private Spinner addClass_SPNR_freeHours;

    public AddClassFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_class, container, false);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference();
        setImageResource = new ReplacingGymClassChoice();
        findViews(view);
        getTrainerData();
        clickSelectDate();
        chooseClassName();
        confirm();
        return view;
    }



    private void findViews(View view) {
        addClass_TXT_selectDate = view.findViewById(R.id.addClass_TXT_selectDate);
        addClass_BTN_availableHours = view.findViewById(R.id.addClass_BTN_availableHours);
        addClass_SPNR_freeHours = view.findViewById(R.id.addClass_SPNR_freeHours);
        addClass_PIC_pilates = view.findViewById(R.id.addClass_PIC_pilates);
        addClass_PIC_yoga = view.findViewById(R.id.addClass_PIC_yoga);
        addClass_PIC_dance = view.findViewById(R.id.addClass_PIC_dance);
        addClass_PIC_cycling = view.findViewById(R.id.addClass_PIC_cycling);
        addClass_PIC_crossfit = view.findViewById(R.id.addClass_PIC_crossfit);
        addClass_PIC_boxing = view.findViewById(R.id.addClass_PIC_boxing);
        addClass_BTN_addClass = view.findViewById(R.id.addClass_BTN_addClass);
        boxing_name= view.findViewById(R.id.boxing_name);
        pilates_name= view.findViewById(R.id.pilates_name);
        yoga_name= view.findViewById(R.id.yoga_name);
        crossfit_name= view.findViewById(R.id.crossfit_name);
        dance_name= view.findViewById(R.id.dance_name);
        cycling_name= view.findViewById(R.id.cycling_name);
        addClass_SPNR_freeHours.setOnItemSelectedListener(this);
    }


    private void clickSelectDate() {
        addClass_TXT_selectDate.setOnClickListener(v -> {
            year = currentDate.getYear();
            month = currentDate.getMonthValue();
            day = currentDate.getDayOfMonth();
            Log.d("today",currentDate+"");
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    addClass_TXT_selectDate.setText(dayOfMonth + " / " + month + " / " + year);
                    pickedDate = LocalDate.of(year, month, dayOfMonth);
                    Log.d("new format date",pickedDate.toString());
                    showAvailableHoursClick();

                }
            }, year, month, day);
            datePickerDialog.show();
        });
    }

    private void showAvailableHours() {
        if (checkDate()) {
            hoursAdapter = new HoursAdapter(getContext(), trainer.showFreeHours(pickedDate));
            addClass_SPNR_freeHours.setAdapter(hoursAdapter);
        } else {
            hoursAdapter = new HoursAdapter(getContext(), null);
            addClass_SPNR_freeHours.setAdapter(hoursAdapter);
            addClass_BTN_addClass.setVisibility(View.INVISIBLE);
        }

    }

    private void showAvailableHoursClick() {
        addClass_BTN_availableHours.setOnClickListener(v -> {
            showAvailableHours();
        });
    }


    private boolean checkDate() {
        if (pickedDate.isBefore(currentDate)) {
            Log.d("date", Finals.futureDate);
            addClass_TXT_selectDate.setError(Finals.futureDate);
            MySignal.getInstance().toast(Finals.futureDate);
            addClass_TXT_selectDate.requestFocus();
            return false;

        } else if (addClass_TXT_selectDate.getText().equals("Select date")) {
            Log.d("date", Finals.emptyFields);
            addClass_TXT_selectDate.setError(Finals.emptyFields);
            addClass_TXT_selectDate.setError(Finals.emptyFields);
            addClass_TXT_selectDate.requestFocus();
            return false;
        }

        addClass_TXT_selectDate.setError(null);
        return true;
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


    private void chooseClassName() {
        boxing();
        pilates();
        yoga();
        cycling();
        crossfit();
        dance();
    }


    private void boxing() {
        addClass_PIC_boxing.setOnClickListener(view -> {
            if (currentClassName != Finals.classNames.BOXING) {
                setImageResource.boxingPressed(addClass_PIC_boxing, addClass_PIC_crossfit, addClass_PIC_cycling, addClass_PIC_dance,
                        addClass_PIC_pilates, addClass_PIC_yoga,boxing_name,pilates_name,yoga_name,crossfit_name,dance_name,cycling_name);
                currentClassName = Finals.classNames.BOXING;
                showCalendar();
            }

        });
    }

    private void pilates() {
        addClass_PIC_pilates.setOnClickListener(view -> {
            if (currentClassName != Finals.classNames.PILATES) {
                setImageResource.pilatesPressed(addClass_PIC_boxing, addClass_PIC_crossfit, addClass_PIC_cycling, addClass_PIC_dance,
                        addClass_PIC_pilates, addClass_PIC_yoga,boxing_name,pilates_name,yoga_name,crossfit_name,dance_name,cycling_name);
                currentClassName = Finals.classNames.PILATES;
                showCalendar();
            }
        });
    }

    private void yoga() {
        addClass_PIC_yoga.setOnClickListener(view -> {
            if (currentClassName != Finals.classNames.YOGA) {
                setImageResource.yogaPressed(addClass_PIC_boxing, addClass_PIC_crossfit, addClass_PIC_cycling, addClass_PIC_dance,
                        addClass_PIC_pilates, addClass_PIC_yoga,boxing_name,pilates_name,yoga_name,crossfit_name,dance_name,cycling_name);
                currentClassName = Finals.classNames.YOGA;
                showCalendar();
            }

        });
    }


    private void cycling() {
        addClass_PIC_cycling.setOnClickListener(view -> {
            if (currentClassName != Finals.classNames.CYCLING) {
                setImageResource.cyclingPressed(addClass_PIC_boxing, addClass_PIC_crossfit, addClass_PIC_cycling, addClass_PIC_dance,
                        addClass_PIC_pilates, addClass_PIC_yoga,boxing_name,pilates_name,yoga_name,crossfit_name,dance_name,cycling_name);
                currentClassName = Finals.classNames.CYCLING;
                showCalendar();
            }
        });
    }

    private void crossfit() {
        addClass_PIC_crossfit.setOnClickListener(view -> {
            if (currentClassName != Finals.classNames.CROSSFIT) {
                setImageResource.crossfitPressed(addClass_PIC_boxing, addClass_PIC_crossfit, addClass_PIC_cycling,
                        addClass_PIC_dance, addClass_PIC_pilates, addClass_PIC_yoga,boxing_name,pilates_name,yoga_name,crossfit_name,dance_name,cycling_name);
                currentClassName = Finals.classNames.CROSSFIT;
                showCalendar();
            }

        });
    }

    private void dance() {
        addClass_PIC_dance.setOnClickListener(view -> {
            if (currentClassName != Finals.classNames.DANCE) {
                setImageResource.dancePressed(addClass_PIC_boxing, addClass_PIC_crossfit, addClass_PIC_cycling,
                        addClass_PIC_dance, addClass_PIC_pilates, addClass_PIC_yoga,boxing_name,pilates_name,yoga_name,crossfit_name,dance_name,cycling_name);
                currentClassName = Finals.classNames.DANCE;
                showCalendar();
            }
        });
    }


    private void showAddClassOptions() {
        if (currentClassName != null && !hour.equals("")) {
            addClass_BTN_addClass.setVisibility(View.VISIBLE);
        }
    }

    private void confirm() {
        addClass_BTN_addClass.setOnClickListener(v -> {
            if (!trainer.addClass(currentClassName, Integer.parseInt(hour), pickedDate)) {
                MySignal.getInstance().toast("You already work at that time and date");
            } else {
                MySignal.getInstance().toast("Gym class added :)");
            }
            showAvailableHours();
        });
    }

    private void showCalendar() {
        if (currentClassName != null) {
            addClass_TXT_selectDate.setVisibility(View.VISIBLE);
            addClass_BTN_availableHours.setVisibility(View.VISIBLE);
            addClass_SPNR_freeHours.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        hour = hoursAdapter.getItem(position).toString();
        showAddClassOptions();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}