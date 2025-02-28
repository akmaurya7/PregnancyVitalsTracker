package com.example.pregnancyvitalstracker.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PregnancyDao{

    @Insert
    suspend fun insert(pregInfoLog: PregnancyEntity)

    @Query("SELECT * FROM PregInfoLogTable")
    fun getAllVitalList(): Flow<List<PregnancyEntity>>

}