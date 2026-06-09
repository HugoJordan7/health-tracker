package com.example.healthtracker.feature.medication_routine.view;

import static com.example.healthtracker.domain.service.alarm.MedicationAlarmManager.cancelScheduledMedication;
import static com.example.healthtracker.domain.service.alarm.MedicationAlarmManager.toScheduleMedication;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthtracker.common.util.Listener;
import com.example.healthtracker.databinding.ActivityMedicationRoutineBinding;
import com.example.healthtracker.di.DependencyInjector;
import com.example.healthtracker.domain.model.MedicationFragmentType;
import com.example.healthtracker.domain.model.MedicationRoutine;
import com.example.healthtracker.domain.model.Schedule;
import com.example.healthtracker.feature.medication_routine.MedicationRoutineInterface;
import com.example.healthtracker.feature.medication_routine.presenter.MedicationRoutinePresenter;

import java.util.List;

public class MedicationRoutineActivity extends AppCompatActivity implements MedicationRoutineInterface.View {

    private ActivityMedicationRoutineBinding binding;
    private MedicationRoutineAdapter adapter;
    private MedicationRoutineInterface.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMedicationRoutineBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        presenter = new MedicationRoutinePresenter(this, DependencyInjector.getMedicationRoutineRepository());

        binding.arrowRefsHeader.setOnClickListener(v -> finish());

        adapter = new MedicationRoutineAdapter(
                medicationRoutine -> {
                    MedicationRoutineFragment fragment = MedicationRoutineFragment.newInstance(MedicationFragmentType.EDIT, medicationRoutine);
                    fragment.show(getSupportFragmentManager(), "MedicationRoutineFragment");
                },
                medicationRoutine -> {
                    presenter.updateMedicationRoutine(medicationRoutine);
                    manageAlarms(medicationRoutine);
                }
        );
        binding.rvRoutine.setAdapter(adapter);
        binding.rvRoutine.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        presenter.getAllMedicationRoutines();

        binding.addRoutineButton.setOnClickListener(view1 -> {
            MedicationRoutineFragment fragment = MedicationRoutineFragment.newInstance(MedicationFragmentType.CREATE, null);
            fragment.show(getSupportFragmentManager(), "MedicationRoutineFragment");
        });

    }

    private void manageAlarms(MedicationRoutine medicationRoutine) {
        if (medicationRoutine.isEnabled()) {
            for (Schedule schedule : medicationRoutine.getSchedules()) {
                toScheduleMedication(medicationRoutine, schedule);
            }
        } else {
            for (Schedule schedule : medicationRoutine.getSchedules()) {
                cancelScheduledMedication(medicationRoutine, schedule);
            }
        }
    }

    @Override
    public void displayFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetMedicationRoutinesSuccess(List<MedicationRoutine> medicationRoutines) {
        adapter.setMedicationRoutineList(medicationRoutines);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateMedicationRoutineSuccess(MedicationRoutine medicationRoutine) {
        adapter.addMedicationRoutine(medicationRoutine);
        manageAlarms(medicationRoutine);
    }

    @Override
    public void onUpdateMedicationRoutineSuccess(MedicationRoutine medicationRoutine) {
        adapter.updateMedicationRoutine(medicationRoutine);

        for (Schedule schedule : medicationRoutine.getSchedules()) {
            cancelScheduledMedication(medicationRoutine, schedule);
        }

        if (medicationRoutine.isEnabled()) {
            for (Schedule schedule : medicationRoutine.getSchedules()) {
                toScheduleMedication(medicationRoutine, schedule);
            }
        }
    }

    @Override
    public void onRemoveMedicationRoutineSuccess(MedicationRoutine medicationRoutine) {
        adapter.removeMedicationRoutine(medicationRoutine.getId());
        for (Schedule schedule : medicationRoutine.getSchedules()) {
            cancelScheduledMedication(medicationRoutine, schedule);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
