package com.example.fitfactory;

import android.app.Application;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MySignal.init(this);

    }
}
