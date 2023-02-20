package com.example.fitfactory.Model;

import static com.example.fitfactory.AddToDataBase.updateUserInDB;

import androidx.annotation.NonNull;

import com.example.fitfactory.AddToDataBase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

public class User extends Human {

    private final AddToDataBase addToDataBase = new AddToDataBase();

    public User() {
        super();

    }


    public int reserveClass(GymClass gymClass, ArrayList<GymClass> gymClasses) {
        Calendar rightNow = Calendar.getInstance();
        int hour = rightNow.get(Calendar.HOUR_OF_DAY);
        if (gymClass.isClassFull()) {
            return -1;
        } else if (isUserAvailable(gymClass, gymClasses)) {
            getGymClasses().add(gymClass.getClassUUid());//add class to user
            gymClass.getSignedUsers().add(getUid());//add user to class
            updateUserInDB(this);
            addToDataBase.updateGymClassInDB(gymClass);
            return 1;
        } else if(gymClass.getDate().equals(LocalDate.now().toString()) && gymClass.getStartHour()<= hour){//if class date and hour passed
            return -2;
        } else {
            return 0;

        }
    }


    public boolean isUserAvailable(GymClass newClass, ArrayList<GymClass> gymClasses) {
        if (getGymClasses().isEmpty()) {
            return true;
        }
        for (GymClass myClass : gymClasses) {
            if (myClass.equals(newClass)) {//check if the trainer is already in a class at the same time
                return false;
            }
        }
        return true;
    }


    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
