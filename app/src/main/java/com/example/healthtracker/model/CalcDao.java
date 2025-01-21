package com.example.healthtracker.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CalcDao {

    @Insert
    public void insert(Calc calc);

    @Query("SELECT * from Calc where type = :type")
    public List<Calc> getRegisterByType(String type);

    @Query("DELETE from Calc where type = :type")
    public void deleteAllByType(String type);

}
