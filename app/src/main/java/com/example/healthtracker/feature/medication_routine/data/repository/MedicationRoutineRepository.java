package com.example.healthtracker.feature.medication_routine.data.repository;

import com.example.healthtracker.domain.model.MedicationRoutine;
import com.example.healthtracker.domain.model.Schedule;

import java.util.List;

public interface MedicationRoutineRepository {
    List<MedicationRoutine> getAllMedicationRoutines();
    MedicationRoutine createMedicationRoutine(String name, List<Schedule> schedules, List<Integer> daysOfWeek);
    MedicationRoutine updateMedicationRoutine(MedicationRoutine medicationRoutine);
    void removeMedicationRoutine(String medicationRoutineId);
}
