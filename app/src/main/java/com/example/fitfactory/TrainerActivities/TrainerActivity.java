package com.example.fitfactory.TrainerActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitfactory.Finals;
import com.example.fitfactory.R;
import com.example.fitfactory.RegisterActivity;
import com.example.fitfactory.SignOutCallBack;
import com.example.fitfactory.TrainerActivities.Fragments.AddClassFragment;
import com.example.fitfactory.TrainerActivities.Fragments.ChangeClassFragment;
import com.example.fitfactory.TrainerActivities.Fragments.HomeTrainerFragment;
import com.example.fitfactory.UserActivities.ReplacingFragmentsFunctions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class TrainerActivity extends AppCompatActivity implements SignOutCallBack {


    private Finals.tab currentTab = Finals.tab.HOME;
    private TextView name;
    private ImageView trainer_BTN_home;
    private ImageView trainer_BTN_classes;
    private ImageView trainer_BTN_add;
    private ImageView trainer_BTN_change;
    private LinearLayout trainer_LL_classes;
    private LinearLayout trainer_LL_addClass;
    private LinearLayout trainer_LL_home;
    private LinearLayout trainer_LL_change;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private ScaleAnimation scaleAnimation;
    FirebaseDatabase db = FirebaseDatabase.getInstance();

    private ReplacingFragmentsFunctions fragmentsFunctions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer);
        findViews();
        scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        scaleAnimation.setDuration(200);
        scaleAnimation.setFillAfter(true);
        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.main_screen, HomeTrainerFragment.class, null).commit();
        fragmentsFunctions = new ReplacingFragmentsFunctions();
        check();
    }


    public void findViews() {
        trainer_BTN_add = findViewById(R.id.trainer_BTN_add);
        trainer_BTN_change = findViewById(R.id.trainer_BTN_change);
        trainer_BTN_classes = findViewById(R.id.trainer_BTN_classes);
        trainer_BTN_home = findViewById(R.id.trainer_BTN_home);
        trainer_LL_change = findViewById(R.id.trainer_LL_change);
        trainer_LL_addClass = findViewById(R.id.trainer_LL_addClass);
        trainer_LL_home = findViewById(R.id.trainer_LL_home);
        trainer_LL_classes = findViewById(R.id.trainer_LL_classes);
        name = findViewById(R.id.name);
    }


    public void check() {
        trainer_LL_home.setOnClickListener(view -> {
            if (currentTab != Finals.tab.HOME) {
                getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.main_screen, HomeTrainerFragment.class, null).commit();
                fragmentsFunctions.changeToTrainerHomeFragment(trainer_BTN_home, trainer_BTN_add, trainer_BTN_change,
                        trainer_BTN_classes, trainer_LL_home);
                currentTab = Finals.tab.HOME;
            }

        });

        trainer_LL_change.setOnClickListener(view -> {
            if (currentTab != Finals.tab.CHANGE_CLASS) {
                getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.main_screen, ChangeClassFragment.class, null).commit();
                fragmentsFunctions.changeToTrainerChangeClassFragment(trainer_BTN_home, trainer_BTN_add, trainer_BTN_change,
                        trainer_BTN_classes, trainer_LL_change);
                currentTab = Finals.tab.CHANGE_CLASS;
            }

        });

        trainer_LL_addClass.setOnClickListener(view -> {
            if (currentTab != Finals.tab.ADD_CLASS) {
                getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.main_screen, AddClassFragment.class, null).commit();
                fragmentsFunctions.changeToTrainerAddClassFragment(trainer_BTN_home, trainer_BTN_add, trainer_BTN_change,
                        trainer_BTN_classes, trainer_LL_addClass);
                currentTab = Finals.tab.ADD_CLASS;
            }

        });


    }


    @Override
    public void signOut() {
        mAuth.signOut();
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }
}

