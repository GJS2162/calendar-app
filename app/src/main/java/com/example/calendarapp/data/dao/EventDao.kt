package com.example.calendarapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.calendarapp.data.model.Event

@Dao
interface EventDao {
    @Insert
    suspend fun insertEvent(event: Event)

    @Update
    suspend fun updateEvent(event: Event)

    @Query("SELECT * FROM events WHERE date = :date")
    suspend fun getEventsByDate(date: String): List<Event>

    @Delete
    suspend fun deleteEvent(event: Event)
}
