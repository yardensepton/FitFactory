package com.example.fitfactory;

import com.example.fitfactory.Model.GymClass;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

public class Finals {
    public static String trainers = "Trainers";
    public static String added ="Gym class added :)";
    public static String full = "Sorry the class is full";
    public static  String closed = "The registration is closed";
    public static String occupied ="You already have a class at that time and date";
    public static String signedUsers = "signedUsers";
    public static String date = "date";
    public static String gym_Classes = "Gym classes";
    public static String gymClasses = "gymClasses";
    public static String classUUid = "classUUid";
    public static String users = "Users";

    public static int PILATES_PIC = R.drawable.pilates_color;
    public static int BOXING_PIC = R.drawable.boxing_color;
    public static int CYCLING_PIC = R.drawable.cycling_color;
    public static int YOGA_PIC = R.drawable.yoga_color;
    public static int CROSSFIT_PIC = R.drawable.crossfit_color;
    public static int DANCE_PIC = R.drawable.dance_color;
    public static String user = "user";
    public static String trainerId = "trainerId";
    public static String working = "You already work at that time and date";
    public static String emptyFields = "Empty fields";
    public static String incorrectEmail = "Incorrect email address";
    public static String futureDate = "Select a future date";
    public static int START_WORK_DAY = 8;
    public static int END_WORK_DAY = 21;

    public enum tab {
        HOME, NOTIFICATIONS, CALENDAR,CONTACT,ADD_CLASS

    }

    public enum classNames {
        PILATES, YOGA, CYCLING,BOXING,DANCE,CROSSFIT

    }

    public static  String greetPerson() {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        if (timeOfDay >= 0 && timeOfDay < 12) {
            return "Good Morning,";
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            return "Good Afternoon,";
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            return "Good Evening,";
        } else {
            return "Good Night,";
        }
    }

    public static ArrayList<GymClass> showFutureClasses(ArrayList<GymClass> gymClasses) {
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

}
