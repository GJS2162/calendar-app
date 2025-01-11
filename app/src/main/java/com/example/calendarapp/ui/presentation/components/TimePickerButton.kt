package com.example.calendarapp.ui.presentation.components

import android.app.TimePickerDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import java.util.Calendar

@Composable
fun TimePickerButton(time: String?, onTimeSelected: (String) -> Unit) {
    var timePickerDialog by remember { mutableStateOf<TimePickerDialog?>(null) }
    var formattedTime by remember { mutableStateOf(time ?: "Select Time") }
    val context = LocalContext.current

    // Time Picker Button
    Button(
        onClick = {
            // Create and show TimePickerDialog
            val currentTime = Calendar.getInstance()
            val timePickerDialog = TimePickerDialog(
                context,
                { _, hourOfDay, minute ->
                    // Format and pass back the selected time
                    formattedTime = String.format("%02d:%02d", hourOfDay, minute)
                    onTimeSelected(formattedTime)
                },
                currentTime.get(Calendar.HOUR_OF_DAY),
                currentTime.get(Calendar.MINUTE),
                true // Use 24-hour format
            )
            timePickerDialog.show()
        }
    ) {
        Text(formattedTime)
    }
}