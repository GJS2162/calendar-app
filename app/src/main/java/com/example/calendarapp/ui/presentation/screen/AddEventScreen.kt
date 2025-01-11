package com.example.calendarapp.ui.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.calendarapp.data.model.EventType
import com.example.calendarapp.ui.presentation.components.TimePickerButton
import com.example.calendarapp.ui.viewmodels.EventViewModel

@Composable
fun AddEventScreen(
    eventViewModel: EventViewModel = hiltViewModel(),
    date: String
) {
    var description by remember { mutableStateOf("") }
    var eventType by remember { mutableStateOf(EventType.EVENT) }
    var startTime by remember { mutableStateOf<String?>(null) }
    var endTime by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Text("Add Event", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // Event Type Picker with RadioButtons
        Text("Select Event Type", style = MaterialTheme.typography.bodyLarge)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            RadioButton(
                selected = eventType == EventType.EVENT,
                onClick = { eventType = EventType.EVENT }
            )
            Text("Event")

            RadioButton(
                selected = eventType == EventType.TASK,
                onClick = { eventType = EventType.TASK }
            )
            Text("Task")

            RadioButton(
                selected = eventType == EventType.BIRTHDAY,
                onClick = { eventType = EventType.BIRTHDAY }
            )
            Text("Birthday")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Event Description Input
        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Event Description") },
            modifier = Modifier.fillMaxWidth()
        )

        // Time Range Picker (Visible only for Event or Task)
        if (eventType == EventType.EVENT || eventType == EventType.TASK) {
            Spacer(modifier = Modifier.height(16.dp))

            // Start Time Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Select Start Time", style = MaterialTheme.typography.bodyLarge)
                TimePickerButton(
                    time = startTime,
                    onTimeSelected = { startTime = it }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // End Time Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Select End Time", style = MaterialTheme.typography.bodyLarge)
                TimePickerButton(
                    time = endTime,
                    onTimeSelected = { endTime = it }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Add Event Button
        Button(
            onClick = {
                eventViewModel.addEvent(date, description, eventType, startTime, endTime)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Event")
        }
    }
}

@Composable
@Preview
fun AddEventScreenPreview() {
    AddEventScreen(date = "2023-11-25")
}


