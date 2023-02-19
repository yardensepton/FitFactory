package com.example.fitfactory;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.airbnb.lottie.LottieAnimationView;
import com.example.fitfactory.Model.GymManager;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private FirebaseAuth mAuth;
    private String state;
    private Spinner spinner;
    private EditText name;
    private AppCompatButton signUpButton;
    private AppCompatButton BTN_openRegister;
    private EditText lastName;
    private EditText email;
    private EditText password;
    private LottieAnimationView loading;

    private GymManager gymManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        gymManager= new GymManager();
        findViews();
        signUpClick();
        goToSignInClick();
    }



    private void signUpClick() {
        signUpButton.setOnClickListener(v -> {
            final String personName = name.getText().toString().trim();
            final String personLName = lastName.getText().toString().trim();
            final String personEmail = email.getText().toString().trim();
            String personPassword = password.getText().toString().trim();
            if (checkInfo(personName, personEmail, personPassword, personLName)) {
                addPerson(personName, personEmail, personPassword, personLName);

            }
        });
    }

    private boolean checkInfo(String personName, String personEmail, String personPassword, String personLName) {
        return checkString(personName, name) && checkString(personLName,lastName) && checkString(personEmail,email) && checkString(personPassword,password) && checkMail(personEmail);
    }

    private boolean checkString(String personName,EditText editText) {//todo fix the problem with name
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


    private void addPerson(String personName, String personEmail, String personPassword, String personLName) {
        loading.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(personEmail, personPassword).addOnCompleteListener(task -> {
            loading.setVisibility(View.GONE);
            if (task.isSuccessful()) {
                if (state.equalsIgnoreCase(Finals.user)) {
                    gymManager.createUser(personEmail,personName,personLName,mAuth.getUid());

                } else {
                    gymManager.createTrainer(personEmail,personName,personLName,mAuth.getUid());
                }
                openRegisterActivity();
            } else {
                MySignal.getInstance().toast(String.valueOf(task.getException()));
            }
        });
    }

    private void goToSignInClick(){
        BTN_openRegister.setOnClickListener(v -> {
            openRegisterActivity();
        });
    }
    private void findViews() {
        name = findViewById(R.id.name);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        lastName = findViewById(R.id.LastName);
        loading = findViewById(R.id.loading);
        loading.setAnimation(R.raw.lifting);
        BTN_openRegister = findViewById(R.id.login_BTN_openRegister);
        signUpButton = findViewById(R.id.signup);
        loading.setVisibility(View.GONE);
        spinner = findViewById(R.id.choice);
        setSpinner();
    }

    private void setSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.states, com.google.android.material.R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }


    private void openRegisterActivity() {
        Intent loginIntent = new Intent(this, RegisterActivity.class);
        startActivity(loginIntent);
        finish();
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        state = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
