package com.example.healthtracker.feature.medication_routine.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthtracker.databinding.ActivityMedicationRoutineBinding;

public class MedicationRoutineActivity extends AppCompatActivity {

    private ActivityMedicationRoutineBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMedicationRoutineBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }



}
