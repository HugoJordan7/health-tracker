package com.example.healthtracker.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Calc::class], version = 2)
@TypeConverters(DateConverter::class)
abstract class AppDataBase: RoomDatabase() {

    abstract fun calcDao(): CalcDao

    companion object{

        private var INSTANCE: AppDataBase? = null

        fun getDataBase(context: Context): AppDataBase {
            if(INSTANCE == null) {
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDataBase::class.java,
                        "health_tracker"
                    ).build()
                }
            }
            return INSTANCE as AppDataBase
        }
    }
}