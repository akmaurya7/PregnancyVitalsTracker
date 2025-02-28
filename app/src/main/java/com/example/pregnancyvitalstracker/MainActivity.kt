package com.example.pregnancyvitalstracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.pregnancyvitalstracker.ui.NotificationViewModel
import com.example.pregnancyvitalstracker.ui.screen.HomeScreen
import com.example.pregnancyvitalstracker.ui.screen.PregnancyViewModel
import com.example.pregnancyvitalstracker.ui.theme.PregnancyVitalsTrackerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val pregnancyViewModel by viewModels<PregnancyViewModel>()
    private  val notificationViewModel by viewModels<NotificationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PregnancyVitalsTrackerTheme {
                HomeScreen(
                    pregnancyViewModel,
                    notificationViewModel
                )
            }
        }
    }
}

