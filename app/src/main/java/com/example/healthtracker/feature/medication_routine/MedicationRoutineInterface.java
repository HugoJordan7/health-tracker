package com.example.healthtracker.feature.medication_routine;

import com.example.healthtracker.common.base.BasePresenter;
import com.example.healthtracker.common.base.BaseView;
import com.example.healthtracker.domain.model.MedicationRoutine;
import com.example.healthtracker.domain.model.Schedule;

import java.util.List;

public interface MedicationRoutineInterface {

    interface View extends BaseView {
        void onGetMedicationRoutinesSuccess(List<MedicationRoutine> medicationRoutines);
        void onCreateMedicationRoutineSuccess(MedicationRoutine medicationRoutine);
        void onUpdateMedicationRoutineSuccess(MedicationRoutine medicationRoutine);
        void onRemoveMedicationRoutineSuccess(MedicationRoutine medicationRoutine);
    }

    interface Presenter extends BasePresenter {
        void getAllMedicationRoutines();
        void createMedicationRoutine(String name, List<Schedule> schedules, List<Integer> daysOfWeek);
        void updateMedicationRoutine(MedicationRoutine medicationRoutine);
        void removeMedicationRoutine(MedicationRoutine medicationRoutine);
    }

}
