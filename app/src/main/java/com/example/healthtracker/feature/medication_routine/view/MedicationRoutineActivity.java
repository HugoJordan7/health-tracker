package com.example.healthtracker.feature.medication_routine.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthtracker.common.util.Listener;
import com.example.healthtracker.databinding.ActivityMedicationRoutineBinding;
import com.example.healthtracker.domain.model.MedicationFragmentType;
import com.example.healthtracker.domain.model.MedicationRoutine;
import com.example.healthtracker.domain.model.Schedule;
import com.example.healthtracker.feature.medication_routine.MedicationRoutineInterface;

import java.util.ArrayList;
import java.util.List;

public class MedicationRoutineActivity extends AppCompatActivity implements MedicationRoutineInterface.View {

    private ActivityMedicationRoutineBinding binding;
    private MedicationRoutineAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMedicationRoutineBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapter = new MedicationRoutineAdapter((Listener<MedicationRoutine>) medicationRoutine -> {
            MedicationRoutineFragment fragment = MedicationRoutineFragment.newInstance(MedicationFragmentType.EDIT, medicationRoutine);
            fragment.show(getSupportFragmentManager(), "MedicationRoutineFragment");
        });
        binding.rvRoutine.setAdapter(adapter);
        binding.rvRoutine.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        for (int i = 0; i<8; i++) {
            List<Schedule> schedules = new ArrayList<>();
            schedules.add(new Schedule(12, 0));
            schedules.add(new Schedule(18, 30));
            MedicationRoutine medicationRoutine = new MedicationRoutine("", "Dipirona", "Diariamente", schedules);
            adapter.addMedicationRoutine(medicationRoutine);
        }

        binding.addRoutineButton.setOnClickListener(view1 -> {
            MedicationRoutineFragment fragment = MedicationRoutineFragment.newInstance(MedicationFragmentType.CREATE, null);
            fragment.show(getSupportFragmentManager(), "MedicationRoutineFragment");
        });

    }

    @Override
    public void displayFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateMedicationRoutineSuccess(MedicationRoutine medicationRoutine) {
        adapter.addMedicationRoutine(medicationRoutine);
    }

    @Override
    public void onUpdateMedicationRoutineSuccess(MedicationRoutine medicationRoutine) {
        adapter.updateMedicationRoutine(medicationRoutine);
    }

    @Override
    public void onRemoveMedicationRoutineSuccess(String medicationRoutineId) {
        adapter.removeMedicationRoutine(medicationRoutineId);
    }
}
