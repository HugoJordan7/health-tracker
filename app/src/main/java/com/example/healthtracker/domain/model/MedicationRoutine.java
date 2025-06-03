package com.example.healthtracker.domain.model;

import java.util.UUID;

public class MedicationRoutine {

    private String id;
    private String userId;
    private String name;
    private String frequency;
    private String schedules;

    public MedicationRoutine(String userId, String name, String frequency, String schedules) {
        id = UUID.randomUUID().toString();
        this.userId = userId;
        this.name = name;
        this.frequency = frequency;
        this.schedules = schedules;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getSchedules() {
        return schedules;
    }

    public void setSchedules(String schedules) {
        this.schedules = schedules;
    }
}
