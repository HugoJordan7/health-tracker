package com.example.healthtracker;

import android.app.Application;
import android.content.Context;

import com.example.healthtracker.model.AppDataBase;

public class App extends Application {

    public AppDataBase db;

    private static Context applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        db = AppDataBase.getDataBase(this);
        applicationContext = getApplicationContext();
    }

    public static Context getContext() {
        return applicationContext;
    }
}
