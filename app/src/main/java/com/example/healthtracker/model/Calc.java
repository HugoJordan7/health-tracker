package com.example.healthtracker.model;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

@Entity
public class Calc {

    @PrimaryKey(autoGenerate = true)
    private int id = 0;

    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "result")
    private double res;

    @ColumnInfo(name = "date")
    private Date createdDate;

    @ColumnInfo(name = "situation")
    private String situation = null;

    public Calc(String type, double res, String situation) {
        this.type = type;
        this.res = res;
        this.situation = situation;
        this.createdDate = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getRes() {
        return res;
    }

    public void setRes(double res) {
        this.res = res;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }
}