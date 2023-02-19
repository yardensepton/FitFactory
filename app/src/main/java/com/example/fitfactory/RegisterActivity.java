package com.example.fitfactory;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.airbnb.lottie.LottieAnimationView;
import com.example.fitfactory.Model.Trainer;
import com.example.fitfactory.Model.User;
import com.example.fitfactory.TrainerActivities.TrainerActivity;
import com.example.fitfactory.UserActivities.UserActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private AppCompatButton signIn;
    private AppCompatButton BTN_openLogin;
    private EditText email;
    private EditText password;
    private FirebaseDatabase db;
    private DatabaseReference databaseReference;
    private LottieAnimationView loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference();
        findViews();
        registerButton();
        openLoginActivity();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null){
          getTrainerOrUserData();
        }

    }

    private void findViews() {
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        loading = findViewById(R.id.loading);
        loading.setAnimation(R.raw.lifting);
        signIn = findViewById(R.id.signIn);
        loading.setVisibility(View.GONE);
        BTN_openLogin = findViewById(R.id.register_BTN_openLogin);
    }

    private void registerButton() {
        signIn.setOnClickListener(v -> {
            final String personEmail = email.getText().toString().trim();
            String personPassword = password.getText().toString().trim();
            if (checkInfo(personEmail, personPassword)) {
                getPerson(personEmail, personPassword);

            }
        });
    }

    private void openLoginActivity() {
        BTN_openLogin.setOnClickListener(v -> {
            Intent loginActivity = new Intent(this, LoginActivity.class);
            startActivity(loginActivity);
            finish();
        });
    }

    private boolean checkInfo(String personEmail, String personPassword) {
        return checkString(personEmail, email) && checkString(personPassword, password) && checkMail(personEmail);
    }


    private void getTrainerOrUserData() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(Finals.trainers).child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).exists()) {
                    databaseReference.child(Finals.trainers).child(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Trainer trainer = task.getResult().getValue(Trainer.class);
                            assert trainer != null;
                            openTrainerActivity();
                        } else {
                            MySignal.getInstance().toast(String.valueOf(task.getException()));
                        }
                    });
                }
                if (snapshot.child(Finals.users).child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).exists()) {
                    databaseReference.child(Finals.users).child(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            User user = task.getResult().getValue(User.class);
                            assert user != null;
                            openUserActivity();
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

    private void openUserActivity() {
        Intent userIntent = new Intent(this, UserActivity.class);
        startActivity(userIntent);
        finish();
    }

    private void openTrainerActivity() {
        Intent trainerIntent = new Intent(this, TrainerActivity.class);
        startActivity(trainerIntent);
        finish();
    }


    private void getPerson(String personEmail, String personPassword) {
        loading.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(personEmail, personPassword).addOnCompleteListener(task -> {
            loading.setVisibility(View.GONE);
            if (task.isSuccessful()) {
                getTrainerOrUserData();
            } else {
                MySignal.getInstance().toast(String.valueOf(task.getException()));
            }
        });
    }

    private boolean checkString(String personName, EditText editText) {
        if (personName.isEmpty()) {
            editText.setError(Finals.emptyFields);
            editText.requestFocus();
            return false;
        }
        return true;
    }

    private boolean checkMail(String personEmail) {
        if (!Patterns.EMAIL_ADDRESS.matcher(personEmail).matches()) {
            email.setError(Finals.incorrectEmail);
            email.requestFocus();
            return false;
        }
        return true;
    }



}