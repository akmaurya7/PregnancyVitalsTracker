package com.example.pregnancyvitalstracker.data

import kotlinx.coroutines.flow.Flow

interface PregnancyRepository{

    fun getAllPregnancyLog() : Flow<List<PregnancyEntity>>

    suspend fun insertPregnancyLog(pregLog: PregnancyEntity)
}