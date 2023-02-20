package com.example.fitfactory.UserActivities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitfactory.Finals;
import com.example.fitfactory.R;
import com.example.fitfactory.RegisterActivity;
import com.example.fitfactory.SignOutCallBack;
import com.example.fitfactory.UserActivities.Fragments.CalendarFragment;
import com.example.fitfactory.UserActivities.Fragments.ContactFragment;
import com.example.fitfactory.UserActivities.Fragments.HomeUserFragment;
import com.example.fitfactory.UserActivities.Fragments.NotificationsFragment;
import com.google.firebase.auth.FirebaseAuth;


public class UserActivity extends AppCompatActivity  implements SignOutCallBack{

    private Finals.tab currentTab = Finals.tab.HOME;
    private TextView name;
    private ImageView main_BTN_notification;
    private ImageView main_BTN_home;
    private ImageView main_BTN_contact;
    private ImageView main_BTN_calendar;
    private LinearLayout main_LL_notification;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private LinearLayout main_LL_home;
    private LinearLayout main_LL_contact;
    private LinearLayout main_LL_calendar;
    private HomeUserFragment homeUserFragment;
    private ContactFragment contactFragment;
    private ReplacingFragmentsFunctions fragmentsFunctions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        findViews();
        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.main_screen, homeUserFragment,null).commit();
        fragmentsFunctions = new ReplacingFragmentsFunctions();
        check();
    }


    public void findViews() {
        main_BTN_notification = findViewById(R.id.main_BTN_notification);
        main_BTN_home = findViewById(R.id.main_BTN_home);
        main_BTN_contact = findViewById(R.id.main_BTN_contact);
        main_LL_notification = findViewById(R.id.main_LL_notification);
        main_LL_home = findViewById(R.id.main_LL_home);
        main_LL_contact = findViewById(R.id.main_LL_contact);
        main_BTN_calendar = findViewById(R.id.main_BTN_calendar);
        main_LL_calendar = findViewById(R.id.main_LL_calendar);
        name = findViewById(R.id.name);
        homeUserFragment = new HomeUserFragment();
        contactFragment = new ContactFragment();
    }


    public void check() {
        main_LL_home.setOnClickListener(view -> {
            if (currentTab != Finals.tab.HOME) {
                getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.main_screen, HomeUserFragment.class,null).commit();
                fragmentsFunctions.changeToUserHomeFragment(main_BTN_notification,main_BTN_contact,
                        main_BTN_calendar,main_BTN_home,main_LL_home);
                currentTab = Finals.tab.HOME;
            }

        });

        main_LL_notification.setOnClickListener(view -> {
            if (currentTab != Finals.tab.NOTIFICATIONS) {
                getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.main_screen, NotificationsFragment.class,null).commit();
                fragmentsFunctions.changeToUserNotificationsFragment(main_BTN_notification,main_BTN_contact,
                        main_BTN_calendar,main_BTN_home,main_LL_notification);
                currentTab = Finals.tab.NOTIFICATIONS;
            }

        });

        main_LL_calendar.setOnClickListener(view -> {
            if (currentTab != Finals.tab.CALENDAR) {
                getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.main_screen, CalendarFragment.class,null).commit();
                fragmentsFunctions.changeToUserCalendarFragment(main_BTN_notification,main_BTN_contact,
                        main_BTN_calendar,main_BTN_home,main_LL_calendar);
                currentTab = Finals.tab.CALENDAR;
            }

        });

        main_LL_contact.setOnClickListener(view -> {
            if (currentTab != Finals.tab.CONTACT) {
                getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.main_screen, contactFragment,null).commit();
                fragmentsFunctions.changeToUserContactFragment(main_BTN_notification,main_BTN_contact,
                        main_BTN_calendar,main_BTN_home,main_LL_contact);
                currentTab = Finals.tab.CONTACT;
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