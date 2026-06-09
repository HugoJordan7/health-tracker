package com.example.healthtracker.feature.medication_routine.view;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthtracker.R;
import com.example.healthtracker.common.util.TextViewManager;
import com.example.healthtracker.databinding.FragmentMedicationRoutineBinding;
import com.example.healthtracker.di.DependencyInjector;
import com.example.healthtracker.domain.model.MedicationFragmentType;
import com.example.healthtracker.domain.model.MedicationRoutine;
import com.example.healthtracker.domain.model.Schedule;
import com.example.healthtracker.feature.medication_routine.MedicationRoutineInterface;
import com.example.healthtracker.feature.medication_routine.presenter.MedicationRoutinePresenter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MedicationRoutineFragment extends DialogFragment {

    private MedicationRoutineInterface.View activity;
    private FragmentMedicationRoutineBinding binding;
    private MedicationFragmentType type;

    private MedicationRoutine medicationRoutine;
    private List<Integer> selectedDays = new ArrayList<>();
    private List<TextView> tvDays = new ArrayList<>();

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

        setupDaysOfWeek();

        RecyclerView rvSchedules = binding.rvSchedules;
        rvSchedules.setLayoutManager(new GridLayoutManager(requireContext(), 3));
        SchedulesAdapter schedulesAdapter = new SchedulesAdapter();

        rvSchedules.setAdapter(schedulesAdapter);

        if (medicationRoutine != null) {
            binding.medicationNameEditText.setText(medicationRoutine.getName());
            schedulesAdapter.setScheduleList(medicationRoutine.getSchedules());
            loadSelectedDays(medicationRoutine.getDaysOfWeek());
        }

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
            if (schedulesAdapter.getScheduleList().isEmpty()) {
                Toast.makeText(requireContext(), getString(R.string.schedules_not_found), Toast.LENGTH_SHORT).show();
                return;
            }
            if (selectedDays.isEmpty()) {
                Toast.makeText(requireContext(), "Selecione ao menos um dia da semana", Toast.LENGTH_SHORT).show();
                return;
            }
            if (existErrorsInFields()) return;

            String medicationName = binding.medicationNameEditText.getText().toString();
            List<Schedule> schedules = schedulesAdapter.getScheduleList();

            if (type == MedicationFragmentType.CREATE) {
                presenter.createMedicationRoutine(medicationName, schedules, selectedDays);
            } else {
                MedicationRoutine newMedicationRoutine = new MedicationRoutine(
                        medicationRoutine.getId(),
                        medicationRoutine.getUserId(),
                        medicationName,
                        schedules,
                        selectedDays,
                        medicationRoutine.isEnabled()
                );
                presenter.updateMedicationRoutine(newMedicationRoutine);
            }
            dismiss();
        });

        binding.deleteRoutineButton.setOnClickListener((view2) -> {
            presenter.removeMedicationRoutine(medicationRoutine);
            dismiss();
        });

    }

    private void setupDaysOfWeek() {
        tvDays.add(binding.tvSunday);
        tvDays.add(binding.tvMonday);
        tvDays.add(binding.tvTuesday);
        tvDays.add(binding.tvWednesday);
        tvDays.add(binding.tvThursday);
        tvDays.add(binding.tvFriday);
        tvDays.add(binding.tvSaturday);

        for (int i = 0; i < tvDays.size(); i++) {
            int dayIndex = i + 1; // Calendar.SUNDAY = 1, ..., Calendar.SATURDAY = 7
            TextView tv = tvDays.get(i);
            tv.setOnClickListener(v -> {
                if (selectedDays.contains(dayIndex)) {
                    selectedDays.remove(Integer.valueOf(dayIndex));
                    tv.setSelected(false);
                    tv.setTextColor(getResources().getColor(R.color.black));
                } else {
                    selectedDays.add(dayIndex);
                    tv.setSelected(true);
                    tv.setTextColor(getResources().getColor(R.color.white));
                }
            });
        }
    }

    private void loadSelectedDays(List<Integer> days) {
        if (days == null) return;
        selectedDays.clear();
        selectedDays.addAll(days);
        for (Integer day : selectedDays) {
            int index = day - 1;
            if (index >= 0 && index < tvDays.size()) {
                tvDays.get(index).setSelected(true);
                tvDays.get(index).setTextColor(getResources().getColor(R.color.white));
            }
        }
    }

    private boolean existErrorsInFields() {
        return binding.medicationNameEditText.getError() != null;
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