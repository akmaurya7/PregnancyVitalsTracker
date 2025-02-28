package com.example.pregnancyvitalstracker.data

import android.icu.util.TimeZone.SystemTimeZoneType
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PregInfoLogTable")
data class PregnancyEntity(
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    val pulseRate: Int,
    val SysBp: Int,
    val DiaBp: Int,
    val Weight:Float,
    val BabyKicks: Int,
    val currTime: Long
)