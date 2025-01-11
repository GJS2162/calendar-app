package com.example.calendarapp.ui.presentation.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calendarapp.ui.presentation.components.AnimatedCalendar
import com.example.calendarapp.ui.presentation.components.CalendarGrid
import com.example.calendarapp.ui.presentation.components.CalendarHeader
import kotlinx.coroutines.launch
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreen() {
    val currentMonth = remember { mutableStateOf(LocalDate.now()) }
    val swipeOffset = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()
    val swipeThreshold = 0.3f
    val gapWidth = 0.2f

    val nextMonth = remember { mutableStateOf(LocalDate.now().plusMonths(1)) }
    val prevMonth = remember { mutableStateOf(LocalDate.now().minusMonths(1)) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectHorizontalSwipeGestures(
                    onSwipeComplete = { direction ->
                        scope.launch {
                            when (direction) {
                                SwipeDirection.LEFT -> {
                                    // Swiping left: Move to the next month
                                    currentMonth.value = currentMonth.value.plusMonths(1)
                                    nextMonth.value = nextMonth.value.plusMonths(1)
                                    prevMonth.value = prevMonth.value.plusMonths(1)
                                }
                                SwipeDirection.RIGHT -> {
                                    // Swiping right: Move to the previous month
                                    currentMonth.value = currentMonth.value.minusMonths(1)
                                    nextMonth.value = nextMonth.value.minusMonths(1)
                                    prevMonth.value = prevMonth.value.minusMonths(1)
                                }
                                else -> { }
                            }
                            swipeOffset.animateTo(0f)
                        }
                    },
                    onSwipeProgress = { deltaX ->
                        scope.launch {
                            val normalizedOffset = (deltaX / size.width).coerceIn(-1f, 1f)
                            swipeOffset.snapTo(normalizedOffset)
                        }
                    },
                    swipeThreshold = swipeThreshold
                )
            }
            .padding(16.dp)
    ) {
        val offsetPx = with(LocalDensity.current) { 300.dp.toPx() }

        // Previous Month
        AnimatedCalendar(
            monthOffset = swipeOffset.value - (1 + gapWidth),
            currentMonth = currentMonth.value.minusMonths(1),
            offsetPx = offsetPx
        )

        // Current Month
        AnimatedCalendar(
            monthOffset = swipeOffset.value,
            currentMonth = currentMonth.value,
            offsetPx = offsetPx,
            onClickMonthChange = {
                scope.launch {
                    swipeOffset.animateTo(if (it > 0) -1f else 1f)
                    currentMonth.value = currentMonth.value.plusMonths(it)
                    swipeOffset.snapTo(0f)
                }
            }
        )

        // Next Month
        AnimatedCalendar(
            monthOffset = swipeOffset.value + (1 + gapWidth),
            currentMonth = currentMonth.value.plusMonths(1),
            offsetPx = offsetPx
        )
    }
}

suspend fun PointerInputScope.detectHorizontalSwipeGestures(
    onSwipeComplete: (SwipeDirection) -> Unit,
    onSwipeProgress: (Float) -> Unit,
    swipeThreshold: Float
) {
    var totalDragAmount = 0f

    detectDragGestures(
        onDragStart = { },
        onDrag = { change, dragAmount ->
            change.consume()
            totalDragAmount += dragAmount.x
            onSwipeProgress(totalDragAmount)
        },
        onDragEnd = {
            // Determine if swipe is valid
            val direction = when {
                totalDragAmount / size.width > swipeThreshold -> SwipeDirection.RIGHT
                totalDragAmount / size.width < -swipeThreshold -> SwipeDirection.LEFT
                else -> SwipeDirection.NONE
            }

            onSwipeComplete(direction)
            totalDragAmount = 0f
        },
        onDragCancel = {
            onSwipeComplete(SwipeDirection.NONE)
            totalDragAmount = 0f
        }
    )
}

enum class SwipeDirection {
    LEFT, RIGHT, NONE
}





@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun ScreenPreview(){
    CalendarScreen()
}
