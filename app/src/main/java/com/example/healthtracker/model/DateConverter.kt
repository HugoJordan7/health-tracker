package com.example.healthtracker.model

import androidx.room.TypeConverter
import java.util.*

object DateConverter {

    @TypeConverter
    fun toDate(long: Long?): Date? {
        return if(long != null) Date(long) else null
    }

    @TypeConverter
    fun toLong(date: Date?): Long? {
        return date?.time
    }
}