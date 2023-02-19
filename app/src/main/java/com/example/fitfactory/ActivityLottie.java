package com.example.fitfactory;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class ActivityLottie extends AppCompatActivity {

    private LottieAnimationView lottie_app_loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottie);

        findViews();
        startAnimation(lottie_app_loading);

    }


    private void startAnimation(View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);


        view.setAlpha(1.0f); // Transparent

        view.animate().alpha(1.0f).setDuration(2000) //in milliseconds
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        startApp();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
    }

    private void startApp() {
        startActivity(new Intent(this, RegisterActivity.class));
        finish();
    }



    private void findViews() {
        lottie_app_loading = findViewById(R.id.lottie_app_loading);
        lottie_app_loading.setAnimation(R.raw.gym);
    }
}