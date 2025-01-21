package com.example.healthtracker;

import android.app.Application;

import com.example.healthtracker.model.AppDataBase;

public class App extends Application {

    public AppDataBase db;

    @Override
    public void onCreate() {
        super.onCreate();
        db = AppDataBase.getDataBase(this);
    }
}
