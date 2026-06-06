package com.example.healthtracker.feature.medication_routine.presenter;

import com.example.healthtracker.domain.model.MedicationRoutine;
import com.example.healthtracker.domain.model.Schedule;
import com.example.healthtracker.domain.model.User;
import com.example.healthtracker.feature.medication_routine.MedicationRoutineInterface;
import com.example.healthtracker.feature.medication_routine.data.repository.MedicationRoutineRepository;

import java.util.List;

public class MedicationRoutinePresenter implements MedicationRoutineInterface.Presenter {

    private MedicationRoutineInterface.View view;
    private MedicationRoutineRepository repository;

    public MedicationRoutinePresenter(MedicationRoutineInterface.View view, MedicationRoutineRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void createMedicationRoutine(String name, String frequency, List<Schedule> schedules) {
        try {
            MedicationRoutine medicationRoutine = repository.createMedicationRoutine(name, frequency, schedules);
            view.onCreateMedicationRoutineSuccess(medicationRoutine);
        } catch (Exception e){
            view.displayFailure(e.getMessage());
        }
    }

    @Override
    public void updateMedicationRoutine(MedicationRoutine medicationRoutine) {
        try {
            repository.updateMedicationRoutine(medicationRoutine);
        } catch (Exception e){
            view.displayFailure(e.getMessage());
        }
    }

    @Override
    public void removeMedicationRoutine(String medicationRoutineId) {
        try {
            repository.removeMedicationRoutine(medicationRoutineId);
        } catch (Exception e){
            view.displayFailure(e.getMessage());
        }
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}
