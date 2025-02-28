package com.example.pregnancyvitalstracker.ui.screen

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.pregnancyvitalstracker.ui.AddVitalDialog
import com.example.pregnancyvitalstracker.ui.NotificationViewModel
import com.example.pregnancyvitalstracker.ui.VitalCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    pregnancyViewModel: PregnancyViewModel,
    notificationViewModel: NotificationViewModel
) {
    val context = LocalContext.current

    var hasNotificationPermission by remember {
        mutableStateOf(checkNotificationPermission(context))
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasNotificationPermission = isGranted
        if (isGranted) {
            notificationViewModel.scheduleHourlyNotifications()
        }
    }

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !hasNotificationPermission) {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    val openDialog = remember { mutableStateOf(false) }
    val vitalList by pregnancyViewModel.getPregnancyLog().collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                title = { Text("Track My Pregnancy") },
                actions = {
                    IconButton(onClick = {
                        if (hasNotificationPermission) {
                            Toast.makeText(context, "You have already activated the notifications", Toast.LENGTH_SHORT).show()
                        } else {
                            requestNotificationPermission(permissionLauncher)
                        }
                    }) {
                        if(hasNotificationPermission){
                            Icon(Icons.Default.NotificationsActive, contentDescription = "Notification Enabled")
                        }else{
                            Icon(Icons.Default.NotificationsOff, contentDescription = "Notification Disable")
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                onClick = { openDialog.value = true }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Vitals")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(8.dp)
        ) {
            items(vitalList.sortedByDescending { it.currTime }) { vital ->
                VitalCard(
                    pulseRate = vital.pulseRate,
                    sysBp = vital.SysBp,
                    diaBp = vital.DiaBp,
                    weight = vital.Weight,
                    babyKicks = vital.BabyKicks,
                    timestamp = vital.currTime
                )
                Spacer(modifier = Modifier.padding(12.dp))
            }
        }
    }

    if (openDialog.value) {
        AddVitalDialog(
            onDismissRequest = { openDialog.value = false },
            pregnancyViewModel = pregnancyViewModel
        )
    }
}

// Helper function to check for notification permission.
fun checkNotificationPermission(context: Context): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    } else {
        true
    }
}

private fun requestNotificationPermission(launcher: ActivityResultLauncher<String>) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }
}
