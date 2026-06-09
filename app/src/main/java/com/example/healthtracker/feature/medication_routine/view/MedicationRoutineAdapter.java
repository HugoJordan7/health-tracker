package com.example.healthtracker.feature.medication_routine.view;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthtracker.R;
import com.example.healthtracker.common.util.Listener;
import com.example.healthtracker.domain.model.MedicationRoutine;

import java.util.ArrayList;
import java.util.List;

public class MedicationRoutineAdapter extends RecyclerView.Adapter<MedicationRoutineAdapter.MedicationRoutineViewHolder> {

    private List<MedicationRoutine> medicationRoutineList = new ArrayList<>();
    private Listener<MedicationRoutine> configListener;
    private Listener<MedicationRoutine> toggleListener;

    public MedicationRoutineAdapter(Listener<MedicationRoutine> configListener, Listener<MedicationRoutine> toggleListener) {
        this.configListener = configListener;
        this.toggleListener = toggleListener;
    }

    @NonNull
    @Override
    public MedicationRoutineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.medication_routine_item, parent, false);
        return new MedicationRoutineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicationRoutineViewHolder holder, int position) {
        holder.bind(medicationRoutineList.get(position));
    }

    @Override
    public int getItemCount() {
        return medicationRoutineList.size();
    }

    public void setMedicationRoutineList(List<MedicationRoutine> medicationRoutineList) {
        this.medicationRoutineList = medicationRoutineList;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addMedicationRoutine(MedicationRoutine medicationRoutine) {
        medicationRoutineList.add(medicationRoutine);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateMedicationRoutine(MedicationRoutine medicationRoutine) {
        for (int i = 0; i < medicationRoutineList.size(); i++) {
            if (medicationRoutineList.get(i).getId().equals(medicationRoutine.getId())) {
                medicationRoutineList.set(i, medicationRoutine);
                notifyDataSetChanged();
                break;
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void removeMedicationRoutine(String medicationRoutineId) {
        for (int i = 0; i < medicationRoutineList.size(); i++) {
            if (medicationRoutineList.get(i).getId().equals(medicationRoutineId)) {
                medicationRoutineList.remove(i);
                notifyDataSetChanged();
                break;
            }
        }
    }

    class MedicationRoutineViewHolder extends RecyclerView.ViewHolder {

        public MedicationRoutineViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind(MedicationRoutine medicationRoutine) {
            TextView medicationName = itemView.findViewById(R.id.medication_name);
            medicationName.setText(medicationRoutine.getName());

            TextView medicationDays = itemView.findViewById(R.id.medication_days);
            medicationDays.setText(medicationRoutine.getFormatDaysOfWeek());

            TextView medicationHour = itemView.findViewById(R.id.medication_hour);
            medicationHour.setText(medicationRoutine.getFormatSchedules());

            SwitchCompat activeRoutineButton = itemView.findViewById(R.id.active_routine_button);
            activeRoutineButton.setOnCheckedChangeListener(null); // Clear previous listener to avoid triggering it during bind
            activeRoutineButton.setChecked(medicationRoutine.isEnabled());
            
            activeRoutineButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
                medicationRoutine.setEnabled(isChecked);
                if (toggleListener != null) {
                    toggleListener.run(medicationRoutine);
                }
            });

            itemView.findViewById(R.id.routine_config).setOnClickListener(view -> {
                if (configListener != null) {
                    configListener.run(medicationRoutine);
                }
            });

        }
    }
}
