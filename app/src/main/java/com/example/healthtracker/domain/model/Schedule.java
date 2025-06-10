package com.example.healthtracker.domain.model;

public class Schedule {
    private int hour;
    private int minute;

    public Schedule(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public String getFormatSchedule() {
        return String.format("%02d:%02dh", hour, minute);
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
