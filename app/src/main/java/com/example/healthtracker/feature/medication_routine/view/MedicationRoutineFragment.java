package com.example.healthtracker.feature.medication_routine.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.healthtracker.R;
import com.example.healthtracker.common.util.Listener;
import com.example.healthtracker.databinding.FragmentMedicationRoutineBinding;
import com.example.healthtracker.domain.model.MedicationFragmentType;

public class MedicationRoutineFragment extends DialogFragment {

    private FragmentMedicationRoutineBinding binding;
    private MedicationFragmentType type;
    private Listener listener;
    public MedicationRoutineFragment() {
        // Required empty public constructor
    }

    public static MedicationRoutineFragment newInstance(MedicationFragmentType type, Listener listener) {
        MedicationRoutineFragment fragment = new MedicationRoutineFragment();
        fragment.type = type;
        fragment.listener = listener;
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMedicationRoutineBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (type == MedicationFragmentType.CREATE){
            binding.saveButton.setText(getString(R.string.create));
            binding.deleteRoutineButton.setVisibility(View.INVISIBLE);
        } else{
            binding.saveButton.setText(getString(R.string.save));
            binding.deleteRoutineButton.setVisibility(View.VISIBLE);
        }

        binding.cancelButton.setOnClickListener(view1 -> {
            dismiss();
        });
        binding.saveButton.setOnClickListener(view1 -> {
            listener.run();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}