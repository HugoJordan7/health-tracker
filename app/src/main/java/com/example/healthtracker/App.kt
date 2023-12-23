package com.example.healthtracker

import android.app.Application
import com.example.healthtracker.model.AppDataBase

class App: Application() {
    lateinit var db: AppDataBase
    override fun onCreate(){
        super.onCreate()
        db = AppDataBase.getDataBase(this)
    }
}