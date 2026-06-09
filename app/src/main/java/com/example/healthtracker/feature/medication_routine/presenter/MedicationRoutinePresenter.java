package com.example.healthtracker.feature.medication_routine.presenter;

import com.example.healthtracker.domain.model.MedicationRoutine;
import com.example.healthtracker.domain.model.Schedule;
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
    public void getAllMedicationRoutines() {
        try {
            List<MedicationRoutine> medicationRoutines = repository.getAllMedicationRoutines();
            view.onGetMedicationRoutinesSuccess(medicationRoutines);
        } catch (Exception e){
            view.displayFailure(e.getMessage());
        }
    }

    @Override
    public void createMedicationRoutine(String name, List<Schedule> schedules, List<Integer> daysOfWeek) {
        try {
            MedicationRoutine medicationRoutine = repository.createMedicationRoutine(name, schedules, daysOfWeek);
            view.onCreateMedicationRoutineSuccess(medicationRoutine);
        } catch (Exception e){
            view.displayFailure(e.getMessage());
        }
    }

    @Override
    public void updateMedicationRoutine(MedicationRoutine medicationRoutine) {
        try {
            repository.updateMedicationRoutine(medicationRoutine);
            view.onUpdateMedicationRoutineSuccess(medicationRoutine);
        } catch (Exception e){
            view.displayFailure(e.getMessage());
        }
    }

    @Override
    public void removeMedicationRoutine(MedicationRoutine medicationRoutine) {
        try {
            repository.removeMedicationRoutine(medicationRoutine.getId());
            view.onRemoveMedicationRoutineSuccess(medicationRoutine);
        } catch (Exception e){
            view.displayFailure(e.getMessage());
        }
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}
