package com.example.fitfactory.Model;

import java.util.ArrayList;

public abstract class Human {
    private String name ="";
    private String uid = "";
    private String lastName = "";
    private String email = "";
    private ArrayList<String> gymClasses = new ArrayList<>();

    public Human() {
    }

    public ArrayList<String> getGymClasses() {
        return gymClasses;
    }

    public void setGymClasses(ArrayList<String> gymClasses) {
        this.gymClasses = gymClasses;
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


    @Override
    public String toString() {
        return "Human";
    }
}
