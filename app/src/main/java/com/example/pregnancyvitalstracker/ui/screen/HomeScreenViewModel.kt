package com.example.pregnancyvitalstracker.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pregnancyvitalstracker.data.PregnancyEntity
import com.example.pregnancyvitalstracker.data.PregnancyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PregnancyViewModel @Inject constructor(private val pregnancyRepository: PregnancyRepository) : ViewModel() {

    fun insertVitalLog(id: Int,pulseRate: Int, sysBp: Int, diaBp: Int, weight: Float, babyKicks: Int,currTime: Long) {
        viewModelScope.launch {
            pregnancyRepository.insertPregnancyLog(PregnancyEntity(id, pulseRate,sysBp, diaBp, weight, babyKicks, currTime))
        }
    }

    fun getPregnancyLog(): Flow<List<PregnancyEntity>> {
        return pregnancyRepository.getAllPregnancyLog()
    }
}
