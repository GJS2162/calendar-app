package com.example.calendarapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "events")
data class Event(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String,
    val title: String,
    val type: EventType,
    val startTime: String? = null,
    val endTime: String? = null
)

enum class EventType {
    EVENT,
    TASK,
    BIRTHDAY
}
