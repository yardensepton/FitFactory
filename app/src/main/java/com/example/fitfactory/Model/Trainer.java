package com.example.fitfactory.Model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.fitfactory.AddToDataBase;
import com.example.fitfactory.Finals;
import com.example.fitfactory.GetDataFromDataBase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Trainer extends Human {

    private AddToDataBase addToDataBase = new AddToDataBase();
    private GetDataFromDataBase getDataFromDataBase = new GetDataFromDataBase();
    public Trainer() {
        super();
    }

    public boolean addClass(Finals.classNames name, int hour, LocalDate date,ArrayList<GymClass>gymClasses) {
        GymClass newClass = new GymClass();
        newClass.setName(name).setStartHour(hour).setFinishedHour().setDate(date).setTrainerId(this).setImageRes().setTeacherName(this.getName()).setSignedUsers(new ArrayList<>());
        if (isTrainerAvailable(newClass,gymClasses)) {
            addToDataBase.updateGymClassInDB(newClass);
            getGymClasses().add(newClass.getClassUUid());
            addToDataBase.updateTrainerInDB(this);
            return true;
        }
        return false;
    }




    public boolean isTrainerAvailable(GymClass newClass,ArrayList<GymClass>gymClasses) {
        Log.d("check available","");
        if (getGymClasses().isEmpty()){
            Log.d("empty available","");
            return true;
        }
        for (GymClass myClass : gymClasses) {
            Log.d("inside array available","");
            if (myClass.equals(newClass)) {//check if the trainer is already in a class at the same time
                return false;
            }
        }
        return true;
    }

//    public boolean isTrainerAvailableAtTheNewDate(GymClass newClass, LocalDate newDate) {
//        for (GymClass myClass : getGymClasses().values()) {
//            if (myClass.getDate().equals(newDate.toString()) && myClass.getStartHour() == newClass.getStartHour()) {//check if the trainer is already in a class at the same time
//                return false;
//            }
//        }
//        return true;
//    }


//    public void removeClass(GymClass gymClass) {
//        getGymClasses().remove(gymClass.getClassUUid(), gymClass);
//    }
//
//    public void changeClassTime(GymClass gymClass, int hour) {
//        if (isTrainerAvailable(gymClass)) {
//            Objects.requireNonNull(getGymClasses().get(gymClass.getClassUUid())).setStartHour(hour).setFinishedHour();
//        }
//    }
//
//    public void changeClassDate(GymClass gymClass, LocalDate date) {
//        if (isTrainerAvailableAtTheNewDate(gymClass, date)) {
//            Objects.requireNonNull(getGymClasses().get(gymClass.getClassUUid())).setDate(date);
//
//        }
//    }

    public ArrayList<Integer> showFreeHours(LocalDate date,ArrayList<GymClass>gymClasses) {
        Calendar rightNow = Calendar.getInstance();
        int now = rightNow.get(Calendar.HOUR_OF_DAY);
        int start;
        if (date.equals(LocalDate.now())&& now>7){
            start = now+1;
        }
        else{
            start = Finals.START_WORK_DAY;
        }

        HashMap<Integer,GymClass> timeOfClassesInTheDate = new HashMap<>();
        ArrayList<Integer> availableHours = new ArrayList<>();
        for (GymClass thisClass : gymClasses) {
            if (thisClass.getDate().equals(date.toString())){
                timeOfClassesInTheDate.put(thisClass.getStartHour(),thisClass);
            }
        }
        if (timeOfClassesInTheDate.isEmpty()){
            for (int i =start; i < Finals.END_WORK_DAY ; i++) {
                availableHours.add(i);
            }
        }else {
            for (int i = start; i < Finals.END_WORK_DAY ; i++) {
                if (!timeOfClassesInTheDate.containsKey(i)){//if there isn't a class at that time - add it to the list
                    availableHours.add(i);
                }
            }
        }
        return availableHours;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}