package com.example.healthtracker.domain.service;

import com.example.healthtracker.domain.model.MedicationRoutine;
import com.example.healthtracker.domain.model.Schedule;
import com.example.healthtracker.domain.model.User;
import com.example.healthtracker.model.Calc;

import java.util.List;

public interface HealthTrackerService {
    User postUser(User user);

    User getUser(String email);

    List<Calc> getAllCalcs(String type);

    boolean clearCalcs(String type);

    void insertCalc(Calc calc);

    MedicationRoutine postMedicationRoutine(String name, String frequency, List<Schedule> schedules);

    MedicationRoutine putMedicationRoutine(MedicationRoutine medicationRoutine);

    void removeMedicationRoutine(String medicationRoutineId);
}
