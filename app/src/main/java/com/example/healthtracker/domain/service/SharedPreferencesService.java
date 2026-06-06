package com.example.healthtracker.domain.service;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesService {

    private Context context;

    public SharedPreferencesService(Context context) {
        this.context = context;
    }

    public void saveEmail(String email) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_email", email);
        editor.apply();
    }

    public String getEmail() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_email", null);
    }

}
