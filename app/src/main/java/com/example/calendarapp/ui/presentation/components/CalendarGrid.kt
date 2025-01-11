package com.example.calendarapp.ui.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CalendarGrid(
    daysInMonth: Int,
    firstDayOfWeek: Int,
    onDayClick: (Int) -> Unit
) {
    val weeks = (daysInMonth + firstDayOfWeek) / 7 + 1
    val dayList = (1..daysInMonth).toList()

    Column {
        // Day names header
        Row(modifier = Modifier.fillMaxWidth()) {
            listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat").forEach { day ->
                Text(
                    text = day,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
        }
        // Dates
        for (week in 0 until weeks) {
            Row(modifier = Modifier.fillMaxWidth()) {
                (0..6).forEach { dayOfWeek ->
                    val index = week * 7 + dayOfWeek - firstDayOfWeek
                    val day = dayList.getOrNull(index)
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .padding(4.dp)
                            .clickable { if (day != null) onDayClick(day) },
                        contentAlignment = Alignment.Center
                    ) {
                        if (day != null) {
                            Text(text = day.toString())
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun CalendarGridPreview() {
    CalendarGrid(
        daysInMonth = 31,
        firstDayOfWeek = 1,
        onDayClick = {}
    )
}