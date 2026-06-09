package com.example.healthtracker.domain.service.alarm;

import static android.content.Context.ALARM_SERVICE;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.healthtracker.App;
import com.example.healthtracker.domain.model.MedicationRoutine;
import com.example.healthtracker.domain.model.Schedule;

import java.util.Calendar;
import java.util.List;

public class MedicationAlarmManager {

    private static int generateRequestCode(MedicationRoutine medicationRoutine, Schedule schedule) {
        return (medicationRoutine.getId() + schedule.getFormatSchedule()).hashCode();
    }

    public static void toScheduleMedication(MedicationRoutine medicationRoutine, Schedule schedule) {
        Context context = App.getContext();
        Calendar calendar = getCalendar(medicationRoutine, schedule);

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
    }

    @NonNull
    private static Calendar getCalendar(MedicationRoutine medicationRoutine, Schedule schedule) {
        Calendar now = Calendar.getInstance();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, schedule.getHour());
        calendar.set(Calendar.MINUTE, schedule.getMinute());
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        List<Integer> daysOfWeek = medicationRoutine.getDaysOfWeek();
        if (daysOfWeek != null && !daysOfWeek.isEmpty()) {
            int diff = -1;
            for (int i = 0; i <= 7; i++) {
                int dayToCheck = ((now.get(Calendar.DAY_OF_WEEK) - 1 + i) % 7) + 1;
                if (daysOfWeek.contains(dayToCheck)) {
                    if (i == 0) {
                        if (calendar.after(now)) {
                            diff = 0;
                            break;
                        }
                    } else {
                        diff = i;
                        break;
                    }
                }
            }
            if (diff == -1) {
                diff = 7;
            }
            calendar.add(Calendar.DAY_OF_YEAR, diff);
        } else {
            if (!calendar.after(now)) {
                calendar.add(Calendar.DAY_OF_YEAR, 1);
            }
        }
        return calendar;
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
