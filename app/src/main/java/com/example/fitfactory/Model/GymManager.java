package com.example.fitfactory.Model;

import com.example.fitfactory.AddToDataBase;

public class GymManager {
    private AddToDataBase db = new AddToDataBase();

    public GymManager() {

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
