package com.example.calendarapp.ui.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.calendarapp.data.model.Event
import com.example.calendarapp.data.model.EventType
import com.example.calendarapp.ui.presentation.components.TimePickerButton
import com.example.calendarapp.ui.viewmodels.EventViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventScreen(
    navController: NavController,
    eventViewModel: EventViewModel = hiltViewModel(),
    date: String,
    event: Event? = null
) {
    var title by remember { mutableStateOf(event?.title ?: "") }
    var eventType by remember { mutableStateOf(event?.type ?: EventType.EVENT) }
    var startTime by remember { mutableStateOf(event?.startTime) }
    var endTime by remember { mutableStateOf(event?.endTime) }


    Scaffold(
        topBar = {

            TopAppBar(
                title = { Text("Add Event") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }
            )
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                Text(
                    text = "Select Event Type",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    EventTypeCard(
                        type = EventType.EVENT,
                        selectedType = eventType,
                        onSelect = { eventType = EventType.EVENT }
                    )

                    EventTypeCard(
                        type = EventType.TASK,
                        selectedType = eventType,
                        onSelect = { eventType = EventType.TASK }
                    )

                    EventTypeCard(
                        type = EventType.BIRTHDAY,
                        selectedType = eventType,
                        onSelect = { eventType = EventType.BIRTHDAY }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Event Title Input
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
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
                        Text(
                            text = "Select Start Time",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
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
                        Text(
                            text = "Select End Time",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
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
                        if(event != null){
                            eventViewModel.updateEvent(
                                event.copy(
                                    title = title,
                                    type = eventType,
                                    startTime = startTime,
                                    endTime = endTime
                                )
                            )
                        } else {
                            eventViewModel.addEvent(date, title, eventType, startTime, endTime)
                        }
                        navController.popBackStack()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    if(event != null) Text("Edit Event") else Text("Add Event")
                }
            }
        }
    }

}

@Composable
fun EventTypeCard(
    type: EventType,
    selectedType: EventType,
    onSelect: () -> Unit
) {
    val cardColor = when (type) {
        EventType.EVENT -> Color(0xFFB3E5FC)
        EventType.TASK -> Color(0xFFDCEDC8)
        EventType.BIRTHDAY -> Color(0xFFFFCDD2)
    }

    Card(
        modifier = Modifier
            .clickable { onSelect() },
        colors = CardDefaults.cardColors(cardColor),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, bottom = 5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            RadioButton(
                selected = selectedType == type,
                onClick = onSelect
            )

            Text(
                text = type.name,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}


