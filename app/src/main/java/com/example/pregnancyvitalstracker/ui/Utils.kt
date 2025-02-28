package com.example.pregnancyvitalstracker.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.pregnancyvitalstracker.R
import com.example.pregnancyvitalstracker.ui.screen.PregnancyViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AddVitalDialog(
    onDismissRequest: () -> Unit,
    pregnancyViewModel: PregnancyViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    val pulsRate = remember { mutableStateOf("") }
    val sysBp = remember { mutableStateOf("") }
    val diaBp = remember { mutableStateOf("") }
    val weight = remember { mutableStateOf("") }
    val babyKicks = remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            tonalElevation = 4.dp
        ) {
            val context = LocalContext.current
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Add Vitals", fontSize = 20.sp, fontWeight = FontWeight.Bold)

                OutlinedTextField(
                    value = pulsRate.value,
                    onValueChange = { pulsRate.value = it },
                    label = { Text("Pulse Rate") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = sysBp.value,
                        onValueChange = { sysBp.value = it },
                        label = { Text("Sys BP") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = diaBp.value,
                        onValueChange = { diaBp.value = it },
                        label = { Text("Dia BP") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                }

                OutlinedTextField(
                    value = weight.value,
                    onValueChange = { weight.value = it },
                    label = { Text("Weight (kg)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = babyKicks.value,
                    onValueChange = { babyKicks.value = it },
                    label = { Text("Baby Kicks") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if(pulsRate.value.isNotEmpty() && sysBp.value.isNotEmpty() && sysBp.value.isNotEmpty() && diaBp.value.isNotEmpty() && weight.value.isNotEmpty() && babyKicks.value.isNotEmpty() ) {
                            coroutineScope.launch {
                                pregnancyViewModel.insertVitalLog(
                                    id = System.currentTimeMillis().toInt(),
                                    pulseRate = pulsRate.value.toInt(),
                                    sysBp = sysBp.value.toInt(),
                                    diaBp = diaBp.value.toInt(),
                                    weight = weight.value.toFloat(),
                                    babyKicks = babyKicks.value.toInt(),
                                    currTime = System.currentTimeMillis()
                                )
                                onDismissRequest()
                            }
                        } else{
                            Toast.makeText(context,"Fill All Details ",Toast.LENGTH_SHORT).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF894CAF)),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Submit", color = Color.White, fontSize = 16.sp)
                }
            }
        }
    }
}
@Composable
fun VitalCard(
    pulseRate: Int,
    sysBp: Int,
    diaBp: Int,
    weight: Float,
    babyKicks: Int,
    timestamp: Long
) {
    val formattedDateTime = SimpleDateFormat("EEE, dd MMM yyyy hh:mm a", Locale.getDefault()).format(Date(timestamp))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFEBB9FE), shape = RoundedCornerShape(12.dp))

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp,  8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(modifier = Modifier.weight(1f).padding(0.dp,8.dp)) {
                VitalItem(R.drawable.heart_rate, "$pulseRate bpm")
                VitalItem(R.drawable.scale, "$weight kg")
            }
            Column(modifier = Modifier.weight(1f).padding(0.dp,8.dp)) {
                VitalItem(R.drawable.blood_pressure, "$sysBp/$diaBp mmHg")
                VitalItem(R.drawable.newborn, "$babyKicks kicks")
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF9C4DB9), shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp))
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Text(
                text = formattedDateTime,
                color = Color.White,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun VitalItem(iconRes: Int, value: String) {
    Row(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Text(text = value, fontSize = 16.sp, fontWeight = FontWeight.Medium)
    }
}

