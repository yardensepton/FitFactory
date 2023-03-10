package com.example.fitfactory.UserActivities;

import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.fitfactory.R;

public class ReplacingFragmentsFunctions {

    private ScaleAnimation scaleAnimation;

    public ReplacingFragmentsFunctions() {
    }

    public void changeToUserHomeFragment(ImageView main_BTN_contact
            , ImageView main_BTN_calendar, ImageView main_BTN_home, LinearLayout main_LL_home) {
        main_BTN_contact.setImageResource(R.drawable.call_us);
        main_BTN_calendar.setImageResource(R.drawable.calender);
        main_BTN_home.setImageResource(R.drawable.selected_home);
        createAnim();
        main_LL_home.startAnimation(scaleAnimation);
    }

    public void changeToUserCalendarFragment(ImageView main_BTN_contact, ImageView main_BTN_calendar, ImageView main_BTN_home, LinearLayout main_LL_calendar) {
        main_BTN_contact.setImageResource(R.drawable.call_us);
        main_BTN_calendar.setImageResource(R.drawable.selected_calendar);
        main_BTN_home.setImageResource(R.drawable.home);
        createAnim();
        main_LL_calendar.startAnimation(scaleAnimation);
    }

    public void changeToUserContactFragment(ImageView main_BTN_contact,ImageView main_BTN_calendar, ImageView main_BTN_home, LinearLayout main_LL_contact) {
        main_BTN_calendar.setImageResource(R.drawable.calender);
        main_BTN_home.setImageResource(R.drawable.home);
        main_BTN_contact.setImageResource(R.drawable.selected_call_us);
        createAnim();
        main_LL_contact.startAnimation(scaleAnimation);
    }

    public void createAnim() {
        scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        scaleAnimation.setDuration(200);
        scaleAnimation.setFillAfter(true);
    }


    public void changeToTrainerHomeFragment(ImageView trainer_BTN_home, ImageView trainer_BTN_add, LinearLayout trainer_LL_home) {
        trainer_BTN_home.setImageResource(R.drawable.selected_home);
        trainer_BTN_add.setImageResource(R.drawable.add_icon);
        createAnim();
        trainer_LL_home.startAnimation(scaleAnimation);
    }


    public void changeToTrainerAddClassFragment(ImageView trainer_BTN_home, ImageView trainer_BTN_add, LinearLayout trainer_LL_addClass) {
        trainer_BTN_home.setImageResource(R.drawable.home);
        trainer_BTN_add.setImageResource(R.drawable.selected_add);
        createAnim();
        trainer_LL_addClass.startAnimation(scaleAnimation);
    }


}
