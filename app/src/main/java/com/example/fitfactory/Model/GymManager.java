package com.example.fitfactory.Model;

import com.example.fitfactory.AddToDataBase;
import com.example.fitfactory.GetDataFromDataBase;

import java.util.HashMap;

public class GymManager {
    private AddToDataBase db = new AddToDataBase();
    private GetDataFromDataBase getDataFromDataBase = new GetDataFromDataBase();
    private HashMap<String,GymClass> allGymClasses = new HashMap<>();


    public GymManager() {

    }

    public HashMap<String,GymClass> getAllGymClasses() {
        return allGymClasses;
    }

    public void setAllGymClasses(HashMap<String,GymClass> allGymClasses) {
        this.allGymClasses = allGymClasses;
    }

    public void createTrainer(String personEmail, String personName, String personLName, String id){
        Trainer trainer = new Trainer();
        trainer.setEmail(personEmail).setName(personName).setLastName(personLName).setUid(id);
        db.updateTrainerInDB(trainer);
    }

    public void createUser(String personEmail,String personName, String personLName,String id){
        User user = new User();
        user.setEmail(personEmail).setName(personName).setLastName(personLName).setUid(id);
        db.addUserToDB(user);
    }


}
