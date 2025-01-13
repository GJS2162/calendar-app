package com.example.calendarapp.data.repository

import com.example.calendarapp.data.dao.EventDao
import com.example.calendarapp.data.model.Event
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventRepository @Inject constructor(
    private val eventDao: EventDao
) {
    suspend fun addEvent(event: Event) {
        eventDao.insertEvent(event)
    }

    suspend fun updateEvent(event: Event) {
        eventDao.updateEvent(event)
    }

    suspend fun getEventsByDate(date: String): Flow<List<Event>> {
        return eventDao.getEventsByDate(date)
    }

    suspend fun deleteEvent(event: Event) {
        eventDao.deleteEvent(event)
    }

    suspend fun getEventDatesForMonth(year: Int, month: Int): Set<Int> {
        return eventDao.getEventDatesForMonth(year.toString(), month.toString())
            .mapNotNull { date ->
                // Extract the day of the month from the date (e.g., "2025-1-15" -> 15)
                date.split("-").lastOrNull()?.toIntOrNull()
            }.toSet()
    }
}
