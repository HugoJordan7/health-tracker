package com.example.healthtracker.feature.medication_routine.data.repository;

import com.example.healthtracker.domain.model.MedicationRoutine;
import com.example.healthtracker.domain.model.Schedule;
import com.example.healthtracker.domain.service.HealthTrackerService;

import java.util.List;

public class MedicationRoutineRepositoryImpl implements MedicationRoutineRepository {

    private final HealthTrackerService healthTrackerService;

    public MedicationRoutineRepositoryImpl(HealthTrackerService healthTrackerService) {
        this.healthTrackerService = healthTrackerService;
    }

    @Override
    public MedicationRoutine createMedicationRoutine(String name, String frequency, List<Schedule> schedules) {
        return healthTrackerService.postMedicationRoutine(name, frequency, schedules);
    }

    @Override
    public MedicationRoutine updateMedicationRoutine(MedicationRoutine medicationRoutine) {
        return healthTrackerService.putMedicationRoutine(medicationRoutine);
    }

    @Override
    public void removeMedicationRoutine(String medicationRoutineId) {
        healthTrackerService.removeMedicationRoutine(medicationRoutineId);
    }
}
