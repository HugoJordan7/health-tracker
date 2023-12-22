package com.example.healthtracker.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Calc::class], version = 1)
abstract class AppDataBase: RoomDatabase() {

    abstract fun calcDao(): CalcDao
}