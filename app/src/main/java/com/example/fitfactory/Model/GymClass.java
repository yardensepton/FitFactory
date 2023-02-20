package com.example.fitfactory.Model;

import androidx.annotation.NonNull;

import com.example.fitfactory.Finals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

public class GymClass implements Comparable<GymClass> {

    private final int MAX_USERS = 8;
    private String classUUid;
    private ArrayList<String> signedUsers = new ArrayList<>();

    private int imageRes;
    private Finals.classNames name;
    private String teacherName;
    private String trainerId;
    private String date;
    private int startHour;
    private int finishedHour;
    private boolean isExpandable = false;

    public GymClass() {
        setClassUUid();
    }


    public boolean isExpandable() {
        return isExpandable;
    }

    public void setExpandable(boolean expandable) {
        isExpandable = expandable;
    }

    public String getClassUUid() {
        return classUUid;
    }

    public GymClass setClassUUid() {
        UUID uuid = UUID.randomUUID();
        this.classUUid = uuid.toString();
        return this;
    }

    public ArrayList<String> getSignedUsers() {
        return signedUsers;
    }

    public boolean isClassFull() {
        return signedUsers.size()==getMAX_USERS();
    }

    public GymClass setSignedUsers(ArrayList<String> signedUsers) {
        this.signedUsers = signedUsers;
        return this;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public GymClass setTeacherName(String teacherName) {
        this.teacherName = teacherName;
        return this;
    }

    public int getImageRes() {
        return imageRes;
    }

    public GymClass setImageRes() {
        boxingPic();
        pilatesPic();
        dancePic();
        yogaPic();
        crossfitPic();
        cyclingPic();

        return this;
    }

    private void boxingPic() {
        if (name.equals(Finals.classNames.BOXING)) {
            imageRes = Finals.BOXING_PIC;
        }
    }



    private void pilatesPic() {
        if (name.equals(Finals.classNames.PILATES)) {
            imageRes = Finals.PILATES_PIC;
        }
    }

    private void dancePic() {
        if (name.equals(Finals.classNames.DANCE)) {
            imageRes = Finals.DANCE_PIC;
        }
    }

    private void yogaPic() {
        if (name.equals(Finals.classNames.YOGA)) {
            imageRes = Finals.YOGA_PIC;
        }
    }

    private void crossfitPic() {
        if (name.equals(Finals.classNames.CROSSFIT)) {
            imageRes = Finals.CROSSFIT_PIC;
        }
    }

    private void cyclingPic() {
        if (name.equals(Finals.classNames.CYCLING)) {
            imageRes = Finals.CYCLING_PIC;
        }
    }

    public int getMaxNumUsers() {
        return MAX_USERS;
    }

    public Finals.classNames getName() {
        return name;
    }

    public GymClass setName(Finals.classNames name) {
        this.name = name;
        return this;
    }


    public int getFinishedHour() {
        return finishedHour;
    }

    public GymClass setFinishedHour() {
        this.finishedHour = startHour + 1;
        return this;
    }

    public String getTrainerId() {
        return trainerId;
    }

    public GymClass setTrainerId(Trainer trainer) {
        this.trainerId = trainer.getUid();
        return this;
    }

    public int getMAX_USERS() {
        return MAX_USERS;
    }

    public String getDate() {
        return date;
    }

    public GymClass setDate(LocalDate date) {
        this.date = date.toString();
        return this;
    }

    public int getStartHour() {
        return startHour;
    }

    public GymClass setStartHour(int startHour) {
        this.startHour = startHour;
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GymClass gymClass = (GymClass) o;
        return startHour == gymClass.startHour && date.equals(gymClass.getDate());
    }

    @Override
    public int compareTo(GymClass otherClass) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //convert String to LocalDate
        LocalDate thisDate = LocalDate.parse(getDate(), formatter);
        String otherClassDate = ((GymClass) otherClass).getDate();
        LocalDate otherDate = LocalDate.parse(otherClassDate, formatter);
        if (thisDate.isBefore(otherDate)) {//if this class happens before the other class
            return -1;
        }
        if (thisDate.isEqual(otherDate) && this.getStartHour() < otherClass.getStartHour()) {//if it is the same date- check by hour
            return -1;
        }else if (thisDate.isEqual(otherDate)){
            return 0;
        }
        else {
            return 1;
        }
    }


    public boolean isClassInThePast() {
        Calendar rightNow = Calendar.getInstance();
        int hour = rightNow.get(Calendar.HOUR_OF_DAY);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //convert String to LocalDate
        LocalDate thisDate = LocalDate.parse(getDate(), formatter);
        LocalDate today =LocalDate.now();
        if (thisDate.isBefore(today)) {
            return true;
        } else return thisDate.isEqual(today) && this.getStartHour() < hour;
    }





    @NonNull
    @Override
    public String toString() {
        return name +
                "\nDate: " + date +
                "\nStarts at: " + startHour +":"+"00";
    }
}
