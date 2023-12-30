package com.example.healthtracker.model

import androidx.room.*

@Dao
interface CalcDao {

    @Insert
    fun insert(calc: Calc)

    @Query("SELECT * from Calc where type= :type")
    fun getRegisterByType(type: String): List<Calc>

    @Query("DELETE from Calc where type = :type")
    fun deleteAllByType(type: String)

}