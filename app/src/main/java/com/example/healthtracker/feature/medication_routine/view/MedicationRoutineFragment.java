package com.example.healthtracker.feature.medication_routine.view;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthtracker.R;
import com.example.healthtracker.common.util.Listener;
import com.example.healthtracker.common.util.TextViewManager;
import com.example.healthtracker.databinding.FragmentMedicationRoutineBinding;
import com.example.healthtracker.di.DependencyInjector;
import com.example.healthtracker.domain.model.MedicationFragmentType;
import com.example.healthtracker.domain.model.MedicationRoutine;
import com.example.healthtracker.domain.model.Schedule;
import com.example.healthtracker.feature.medication_routine.MedicationRoutineInterface;
import com.example.healthtracker.feature.medication_routine.presenter.MedicationRoutinePresenter;

import java.util.Calendar;
import java.util.List;

public class MedicationRoutineFragment extends DialogFragment {

    private MedicationRoutineInterface.View activity;
    private FragmentMedicationRoutineBinding binding;
    private MedicationFragmentType type;

    private MedicationRoutine medicationRoutine;

    public MedicationRoutineFragment() {
        // Required empty public constructor
    }

    public static MedicationRoutineFragment newInstance(MedicationFragmentType type, MedicationRoutine medicationRoutine) {
        MedicationRoutineFragment fragment = new MedicationRoutineFragment();
        fragment.type = type;
        fragment.medicationRoutine = medicationRoutine;
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

        MedicationRoutineInterface.Presenter presenter = new MedicationRoutinePresenter(activity, DependencyInjector.getMedicationRoutineRepository());

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

        RecyclerView rvSchedules = binding.rvSchedules;
        rvSchedules.setLayoutManager(new GridLayoutManager(requireContext(), 3));
        SchedulesAdapter schedulesAdapter = new SchedulesAdapter();

        rvSchedules.setAdapter(schedulesAdapter);

        if (medicationRoutine != null) {
            binding.medicationNameEditText.setText(medicationRoutine.getName());
            binding.autoMedicationFrequency.setText(medicationRoutine.getFrequency());
            schedulesAdapter.setScheduleList(medicationRoutine.getSchedules());
        }

        binding.autoMedicationFrequency.setOnClickListener(v -> {
            binding.autoMedicationFrequency.showDropDown();
        });

        AutoCompleteTextView autoMedicationFrequency = binding.autoMedicationFrequency;
        String[] items = getResources().getStringArray(R.array.medication_frequency);
        ArrayAdapter<String> frequencyAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1, items);
        autoMedicationFrequency.setAdapter(frequencyAdapter);

        binding.addScheduleButton.setOnClickListener((view1) -> {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    requireContext(),
                    (view2, selectedHour, selectedMinute) -> {
                        Schedule schedule = new Schedule(selectedHour, selectedMinute);
                        schedulesAdapter.addSchedule(schedule);
                    },
                    hour,
                    minute,
                    true
            );
            timePickerDialog.show();
        });

        binding.saveButton.setOnClickListener((view1) -> {
            TextViewManager.checkIfTextViewIsEmpty(requireContext(), binding.medicationNameEditText);
            TextViewManager.checkIfTextViewIsEmpty(requireContext(), binding.autoMedicationFrequency);
            if (schedulesAdapter.getScheduleList().isEmpty()) {
                Toast.makeText(requireContext(), getString(R.string.schedules_not_found), Toast.LENGTH_SHORT).show();
                return;
            }
            if (existErrorsInFields()) return;

            String medicationName = binding.medicationNameEditText.getText().toString();
            String medicationFrequency = binding.autoMedicationFrequency.getText().toString();
            List<Schedule> schedules = schedulesAdapter.getScheduleList();

            if (type == MedicationFragmentType.CREATE) {
                presenter.createMedicationRoutine(medicationName, medicationFrequency, schedules);
            } else {
                MedicationRoutine newMedicationRoutine = new MedicationRoutine(
                        medicationRoutine.getId(),
                        medicationRoutine.getUserId(),
                        medicationName,
                        medicationFrequency,
                        schedules
                );
                presenter.updateMedicationRoutine(newMedicationRoutine);
                activity.onUpdateMedicationRoutineSuccess(newMedicationRoutine);
            }
            dismiss();
        });

        binding.deleteRoutineButton.setOnClickListener((view2) -> {
            presenter.removeMedicationRoutine(medicationRoutine.getId());
            activity.onRemoveMedicationRoutineSuccess(medicationRoutine);
            dismiss();
        });

    }

    private boolean existErrorsInFields() {
        return binding.medicationNameEditText.getError() != null || binding.autoMedicationFrequency.getError() != null;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MedicationRoutineInterface.View) {
            activity = (MedicationRoutineInterface.View) context;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}