package com.example.pregnancyvitalstracker.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PregnancyRepositoryImpl @Inject constructor(private val pregnancyDao: PregnancyDao) : PregnancyRepository{
    override fun getAllPregnancyLog(): Flow<List<PregnancyEntity>> = pregnancyDao.getAllVitalList()

    override suspend fun insertPregnancyLog(pregLog: PregnancyEntity)  =pregnancyDao.insert(pregLog)

}