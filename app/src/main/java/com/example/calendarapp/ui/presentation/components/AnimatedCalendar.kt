package com.example.calendarapp.ui.presentation.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AnimatedCalendar(monthOffset: Float, currentMonth: LocalDate, offsetPx: Float, onClickMonthChange: (Long) -> Unit = {}) {
    val daysInMonth = currentMonth.lengthOfMonth()
    val firstDayOfWeek = currentMonth.withDayOfMonth(1).dayOfWeek.value % 7

    Column(
        modifier = Modifier
            .fillMaxSize()
            .offset { IntOffset((monthOffset * offsetPx).toInt(), 0) }
    ) {
        CalendarHeader(
            currentMonth = currentMonth,
            onMonthChange = {
                onClickMonthChange(it)
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        CalendarGrid(
            daysInMonth = daysInMonth,
            firstDayOfWeek = firstDayOfWeek,
            onDayClick = { day ->

            }
        )
    }
}


