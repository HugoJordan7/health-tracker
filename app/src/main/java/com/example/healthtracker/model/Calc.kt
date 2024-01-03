package com.example.healthtracker.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Calc(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "result") val res: Double,
    @ColumnInfo(name = "date") val createdDate: Date = Date(),
    @ColumnInfo(name = "situation") var situation: String? = null
)
