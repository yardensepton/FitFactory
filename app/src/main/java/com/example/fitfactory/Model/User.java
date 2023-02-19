package com.example.fitfactory.Model;

import static com.example.fitfactory.AddToDataBase.updateUserInDB;

import android.util.Log;

import com.example.fitfactory.AddToDataBase;
import com.example.fitfactory.GetDataFromDataBase;

import java.util.ArrayList;

public class User extends Human {

    private final AddToDataBase addToDataBase = new AddToDataBase();
    private final GetDataFromDataBase getDataFromDataBase = new GetDataFromDataBase();

    public User() {
        super();

    }


//    public boolean reserveClass(GymClass gymClass,ArrayList<GymClass>gymClasses) {
//        if (isUserAvailable(gymClass)) {
////            getGymClasses().put(gymClass.getClassUUid(), gymClass);
//            getGymClassesUUid().add(gymClass.getClassUUid());//add class to user
//            gymClass.getSignedUsers().add(getUid());//add user to class
//            addToDataBase.updateGymClassInDB(gymClass);
//            updateAllUsersInDB();
//
//            return true;
//        }
//        return false;
//    }

    public boolean reserveClass(GymClass gymClass, ArrayList<GymClass> gymClasses) {
        if (isUserAvailable(gymClass, gymClasses)) {
            getGymClasses().add(gymClass.getClassUUid());//add class to user
            gymClass.getSignedUsers().add(getUid());//add user to class
            updateUserInDB(this);
            addToDataBase.updateGymClassInDB(gymClass);

            return true;
        }
        return false;
    }


    public boolean isUserAvailable(GymClass newClass, ArrayList<GymClass> gymClasses) {
        Log.d("check available", "");
        if (getGymClasses().isEmpty()) {
            Log.d("empty available", "");
            return true;
        }
        Log.d("before array available", "" + gymClasses.size());
        for (GymClass myClass : gymClasses) {
            Log.d("inside array available", "");
            if (myClass.equals(newClass)) {//check if the trainer is already in a class at the same time
                return false;
            }
        }
        return true;
    }


//    public boolean isUserAvailable(GymClass newClass,ArrayList<GymClass>) {
//        Log.d("check available", "");
//        if (getGymClassesUUid().isEmpty()) {
//            Log.d("empty available", "");
//            return true;
//        }
//        ArrayList<GymClass> gymClasses = ();
//        Log.d("size", "" + gymClasses.size());
//        for (GymClass myClass : gymClasses) {
//            Log.d("inside array available", "");
//            if (myClass.equals(newClass)) {//check if the trainer is already in a class at the same time
//                return false;
//            }
//        }
//        return true;
//    }


//    private void cancelClass(GymClass gymClass) {
//        getGymClasses().remove(gymClass.getClassUUid(), gymClass);
//    }


    @Override
    public String toString() {
        return "User{}";
    }
}
