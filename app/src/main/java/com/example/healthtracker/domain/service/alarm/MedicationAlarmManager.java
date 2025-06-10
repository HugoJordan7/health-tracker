package com.example.healthtracker.domain.service.alarm;

import static android.content.Context.ALARM_SERVICE;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.healthtracker.App;
import com.example.healthtracker.domain.model.MedicationRoutine;
import com.example.healthtracker.domain.model.Schedule;

import java.util.Calendar;

public class MedicationAlarmManager {

    private static int generateRequestCode(MedicationRoutine medicationRoutine, Schedule schedule) {
        return (medicationRoutine.getId() + schedule.getFormatSchedule()).hashCode();
    }

    public static void toScheduleMedication(MedicationRoutine medicationRoutine, Schedule schedule) {
        Context context = App.getContext();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, schedule.getHour());
        calendar.set(Calendar.MINUTE, schedule.getMinute());
        calendar.set(Calendar.SECOND, 0);

        if (calendar.before(Calendar.getInstance())) {
            // Se o horário já passou hoje, agenda para amanhã
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("title", "Hora do medicamento");
        intent.putExtra("message", "Você deve tomar o medicamento " + medicationRoutine.getName() + " às " + schedule.getFormatSchedule() + ".");

        int requestCode = generateRequestCode(medicationRoutine, schedule);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        try {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
            alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    pendingIntent
            );
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        Toast.makeText(context, "Lembrete agendado para: " + schedule.getFormatSchedule(), Toast.LENGTH_SHORT).show();
    }

    public static void cancelScheduledMedication(MedicationRoutine medicationRoutine, Schedule schedule) {
        Context context = App.getContext();
        Intent intent = new Intent(context, AlarmReceiver.class);

        int requestCode = generateRequestCode(medicationRoutine, schedule);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

}
