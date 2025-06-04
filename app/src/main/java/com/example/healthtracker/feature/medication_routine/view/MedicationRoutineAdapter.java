package com.example.healthtracker.feature.medication_routine.view;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthtracker.R;
import com.example.healthtracker.common.util.Listener;
import com.example.healthtracker.domain.model.MedicationRoutine;
import com.example.healthtracker.feature.main.view.MainAdapter;

import java.util.ArrayList;
import java.util.List;

public class MedicationRoutineAdapter extends RecyclerView.Adapter<MedicationRoutineAdapter.MedicationRoutineViewHolder> {

    private List<MedicationRoutine> medicationRoutineList = new ArrayList<>();
    private Listener listener;

    public MedicationRoutineAdapter(Listener listener) {
        this.listener = listener;
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

    class MedicationRoutineViewHolder extends RecyclerView.ViewHolder {

        public MedicationRoutineViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind(MedicationRoutine medicationRoutine) {
            TextView medicationName = itemView.findViewById(R.id.medication_name);
            medicationName.setText(medicationRoutine.getName());

            TextView medicationFrequency = itemView.findViewById(R.id.medication_frequency);
            medicationFrequency.setText(medicationRoutine.getFrequency());

            TextView medicationHour = itemView.findViewById(R.id.medication_hour);
            medicationHour.setText(medicationRoutine.getSchedules());

            itemView.findViewById(R.id.routine_config).setOnClickListener(view -> {
                listener.run();
            });

        }
    }
}
