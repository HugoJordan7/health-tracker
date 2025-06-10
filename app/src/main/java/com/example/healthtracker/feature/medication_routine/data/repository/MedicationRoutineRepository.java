package com.example.healthtracker.feature.medication_routine.data.repository;

import com.example.healthtracker.domain.model.MedicationRoutine;
import com.example.healthtracker.domain.model.Schedule;

import java.util.List;

public interface MedicationRoutineRepository {
    MedicationRoutine createMedicationRoutine(String name, String frequency, List<Schedule> schedules);
    MedicationRoutine updateMedicationRoutine(MedicationRoutine medicationRoutine);
    void removeMedicationRoutine(String medicationRoutineId);
}
