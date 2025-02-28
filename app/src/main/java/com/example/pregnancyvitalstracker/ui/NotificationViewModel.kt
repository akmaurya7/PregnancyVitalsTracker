package com.example.pregnancyvitalstracker.ui


import androidx.lifecycle.ViewModel
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val workManager: WorkManager
) : ViewModel() {

    fun scheduleHourlyNotifications() {

        val periodicWorker = PeriodicWorkRequest.Builder(
            NotificationWorker::class.java,
            16, TimeUnit.MINUTES)
            .build()

        workManager.enqueueUniquePeriodicWork(
            NotificationWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
            periodicWorker
        )
    }

    fun cancelNotifications() {
        workManager.cancelUniqueWork(NotificationWorker.WORK_NAME)
    }
}