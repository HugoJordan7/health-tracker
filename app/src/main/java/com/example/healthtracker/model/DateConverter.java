package com.example.healthtracker.model;

import androidx.room.TypeConverter;
import java.util.Date;

public class DateConverter {

    @TypeConverter
    public static Date toDate(Long value) {
        return value != null ? new Date(value) : null;
    }

    @TypeConverter
    public static Long toLong(Date date) {
        return date != null ? date.getTime() : null;
    }
}
