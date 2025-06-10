package com.example.healthtracker.domain.model;

import java.util.List;
import java.util.UUID;

public class MedicationRoutine {

    private String id;
    private String userId;
    private String name;
    private String frequency;
    private List<Schedule> schedules;

    public MedicationRoutine(String userId, String name, String frequency, List<Schedule> schedules) {
        id = UUID.randomUUID().toString();
        this.userId = userId;
        this.name = name;
        this.frequency = frequency;
        this.schedules = schedules;
    }

    public MedicationRoutine(String id, String userId, String name, String frequency, List<Schedule> schedules) {
        this.id = id;
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

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public String getFormatSchedules() {
        StringBuilder schedulesString = new StringBuilder();
        for (int i = 0; i < schedules.size(); i++) {
            Schedule schedule = schedules.get(i);
            String interval = i == schedules.size()-1 ? "h." : "h; ";
            schedulesString.append(schedule.getFormatSchedule()).append(interval);
        }
        return schedulesString.toString();
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }
}
