package com.example.calendarapp.ui.presentation.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarGrid(
    displayedMonth: LocalDate,
    daysInMonth: Int,
    firstDayOfWeek: Int,
    onDayClick: (Int) -> Unit,
    eventsByDate: Set<Int>
) {

    val currentDate = LocalDate.now()
    val isCurrentMonth = currentDate.year == displayedMonth.year && currentDate.month == displayedMonth.month
    val currentDay = if (isCurrentMonth) currentDate.dayOfMonth else null

    val weeks = (daysInMonth + firstDayOfWeek) / 7 + 1
    val dayList = (1..daysInMonth).toList()

    Column {
        // Day names header
        Row(modifier = Modifier.fillMaxWidth()) {
            listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat").forEach { day ->
                Text(
                    text = day,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold
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
                            .clickable {
                                if (day != null){
                                    onDayClick(day)
                                }
                                       },
                        contentAlignment = Alignment.Center
                    ) {
                        if( day != null){
                            if (day == currentDay) {
                                Box(
                                    modifier = Modifier
                                        .size(30.dp)
                                        .background(
                                            color = Color(0xFF6262AB),
                                            shape = CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = day.toString(),
                                        color = Color.White
                                    )
                                }
                            } else {
                                Text(text = day.toString())
                            }

                            // Display event indicator (small circle) below the date
                            if (eventsByDate.contains(day)) {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .align(Alignment.BottomCenter)
                                        .background(
                                            color = Color.Red,
                                            shape = CircleShape
                                        )
                                        .padding(top = 2.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
