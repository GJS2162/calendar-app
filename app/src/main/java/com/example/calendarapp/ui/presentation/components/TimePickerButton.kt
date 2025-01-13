package com.example.calendarapp.ui.presentation.components

import android.app.TimePickerDialog
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.util.Calendar

@Composable
fun TimePickerButton(time: String?, onTimeSelected: (String) -> Unit) {
    var timePickerDialog by remember { mutableStateOf<TimePickerDialog?>(null) }
    var formattedTime by remember { mutableStateOf(time ?: "Select Time") }
    val context = LocalContext.current

    // Time Picker Button
    Button(
        onClick = {
            val currentTime = Calendar.getInstance()
            val timePickerDialog = TimePickerDialog(
                context,
                { _, hourOfDay, minute ->
                    formattedTime = String.format("%02d:%02d", hourOfDay, minute)
                    onTimeSelected(formattedTime)
                },
                currentTime.get(Calendar.HOUR_OF_DAY),
                currentTime.get(Calendar.MINUTE),
                true
            )
            timePickerDialog.show()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFF5F5F5)
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = formattedTime,
            color = Color.Black
        )
    }
}