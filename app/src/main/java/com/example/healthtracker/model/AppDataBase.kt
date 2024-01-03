package com.example.healthtracker.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

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
                    ).addMigrations(MIGRATION_1_2)
                        .build()
                }
            }
            return INSTANCE as AppDataBase
        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Execute o SQL para adicionar a nova coluna 'situation'
                database.execSQL("ALTER TABLE Calc ADD COLUMN situation TEXT")
            }
        }

    }

}