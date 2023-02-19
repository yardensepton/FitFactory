package com.example.fitfactory.Model;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Human {
    private String name ="";
    private String uid = "";
    private String lastName = "";
    private String email = "";
    private HashMap<String, GymClass> gymClasses = new HashMap<>();
    private ArrayList<String> gymClassesUUid = new ArrayList<>();

    public Human() {
    }

    public ArrayList<String> getGymClassesUUid() {
        return gymClassesUUid;
    }

    public void setGymClassesUUid(ArrayList<String> gymClassesUUid) {
        this.gymClassesUUid = gymClassesUUid;
    }

    public String getEmail() {
        return email;
    }

    public String getUid() {
        return uid;
    }

    public Human setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public Human setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getName() {
        return name;
    }

    public Human setName(String name) {
        this.name = name;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Human setLastName(String lName) {
        this.lastName = lName;
        return this;
    }

    public HashMap<String, GymClass> getGymClasses() {
        return gymClasses;
    }

    public Human setGymClasses(HashMap<String, GymClass> gymClasses) {
        this.gymClasses = gymClasses;
        return this;
    }

    @Override
    public String toString() {
        return "Human{" +
//                ", gymClasses=" + gymClasses +
                '}';
    }
}
