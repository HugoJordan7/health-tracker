package com.example.healthtracker.domain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class MedicationRoutine {

    private String id;
    private String userId;
    private String name;
    private List<Schedule> schedules;
    private List<Integer> daysOfWeek;
    private boolean enabled;

    public MedicationRoutine(String userId, String name, List<Schedule> schedules, List<Integer> daysOfWeek) {
        id = UUID.randomUUID().toString();
        this.userId = userId;
        this.name = name;
        this.schedules = schedules;
        this.daysOfWeek = daysOfWeek != null ? daysOfWeek : new ArrayList<>();
        this.enabled = true;
    }

    public MedicationRoutine(String id, String userId, String name, List<Schedule> schedules, List<Integer> daysOfWeek, boolean enabled) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.schedules = schedules;
        this.daysOfWeek = daysOfWeek != null ? daysOfWeek : new ArrayList<>();
        this.enabled = enabled;
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

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public String getFormatSchedules() {
        StringBuilder schedulesString = new StringBuilder();
        for (int i = 0; i < schedules.size(); i++) {
            Schedule schedule = schedules.get(i);
            String interval = i == schedules.size()-1 ? "." : "; ";
            schedulesString.append(schedule.getFormatSchedule()).append(interval);
        }
        return schedulesString.toString();
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public List<Integer> getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(List<Integer> daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getFormatDaysOfWeek() {
        if (daysOfWeek == null || daysOfWeek.isEmpty()) return "";
        if (daysOfWeek.size() == 7) return "Todos os dias";
        
        StringBuilder days = new StringBuilder();
        String[] dayNames = {"Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sab"};
        
        List<Integer> sortedDays = new ArrayList<>(daysOfWeek);
        Collections.sort(sortedDays);

        for (int i = 0; i < sortedDays.size(); i++) {
            int dayIndex = sortedDays.get(i) - 1;
            if (dayIndex >= 0 && dayIndex < dayNames.length) {
                days.append(dayNames[dayIndex]);
                if (i < sortedDays.size() - 1) days.append(", ");
            }
        }
        return days.toString();
    }
}
