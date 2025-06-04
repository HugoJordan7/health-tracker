package com.example.healthtracker.feature.medication_routine.view;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthtracker.common.util.Listener;
import com.example.healthtracker.databinding.ActivityMedicationRoutineBinding;
import com.example.healthtracker.domain.model.MedicationFragmentType;
import com.example.healthtracker.domain.model.MedicationRoutine;

public class MedicationRoutineActivity extends AppCompatActivity {

    private ActivityMedicationRoutineBinding binding;
    private MedicationRoutineAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMedicationRoutineBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapter = new MedicationRoutineAdapter(() -> {
            MedicationRoutineFragment fragment = MedicationRoutineFragment.newInstance(MedicationFragmentType.EDIT, (Listener) () -> {

            });
            fragment.show(getSupportFragmentManager(), "MedicationRoutineFragment");
        });
        binding.rvRoutine.setAdapter(adapter);
        binding.rvRoutine.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        for (int i = 0; i<8; i++) {
            MedicationRoutine medicationRoutine = new MedicationRoutine("", "Dipirona", "Diariamente", "8h e 20h");
            adapter.addMedicationRoutine(medicationRoutine);
        }

        binding.addRoutineButton.setOnClickListener(view1 -> {
            MedicationRoutineFragment fragment = MedicationRoutineFragment.newInstance(MedicationFragmentType.CREATE, (Listener) () -> {

            });
            fragment.show(getSupportFragmentManager(), "MedicationRoutineFragment");
        });

    }



}
