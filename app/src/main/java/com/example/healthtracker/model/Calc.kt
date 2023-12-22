package com.example.healthtracker.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Calc(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "result") var res: Double,
    @ColumnInfo(name = "type") var type: String,
    @ColumnInfo(name = "date") val createdDate: Date = Date()
)
