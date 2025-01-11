package com.example.calendarapp.data.repository

import com.example.calendarapp.data.dao.EventDao
import com.example.calendarapp.data.model.Event
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

    suspend fun getEventsByDate(date: String): List<Event> {
        return eventDao.getEventsByDate(date)
    }

    suspend fun deleteEvent(event: Event) {
        eventDao.deleteEvent(event)
    }
}
