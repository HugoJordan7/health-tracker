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
import com.example.healthtracker.domain.model.Schedule;

import java.util.ArrayList;
import java.util.List;

public class SchedulesAdapter extends RecyclerView.Adapter<SchedulesAdapter.SchedulesViewHolder> {

    private List<Schedule> scheduleList = new ArrayList<>();

    @NonNull
    @Override
    public SchedulesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_item, parent, false);
        return new SchedulesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SchedulesViewHolder holder, int position) {
        holder.bind(scheduleList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

    public List<Schedule> getScheduleList() {
        return scheduleList;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addSchedule(Schedule schedule) {
        scheduleList.add(schedule);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void removeSchedule(int i) {
        if (scheduleList.isEmpty()) return;
        scheduleList.remove(i);
        notifyDataSetChanged();
    }

    public void setScheduleList(List<Schedule> schedules) {
        scheduleList = schedules;
    }

    class SchedulesViewHolder extends RecyclerView.ViewHolder {

        public SchedulesViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind(Schedule schedule, int pos) {
            TextView scheduleTextView = itemView.findViewById(R.id.schedule_text);
            scheduleTextView.setText(schedule.getFormatSchedule());
            itemView.findViewById(R.id.remove_schedule_button).setOnClickListener(view -> removeSchedule(pos));
        }
    }
}
