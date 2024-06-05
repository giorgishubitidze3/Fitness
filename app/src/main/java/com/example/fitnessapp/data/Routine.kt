package com.example.fitnessapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "routines")
data class Routine(
    @PrimaryKey(autoGenerate = true) val routineId: Int = 0,
    val name: String,
    val date: Long = System.currentTimeMillis()
)