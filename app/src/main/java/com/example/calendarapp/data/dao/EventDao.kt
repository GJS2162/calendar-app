package com.example.calendarapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.calendarapp.data.model.Event
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {
    @Insert
    suspend fun insertEvent(event: Event)

    @Update
    suspend fun updateEvent(event: Event)

    @Query("SELECT * FROM events WHERE date = :date")
    fun getEventsByDate(date: String): Flow<List<Event>>

    @Delete
    suspend fun deleteEvent(event: Event)

    @Query("SELECT DISTINCT date FROM events WHERE date LIKE :year || '-' || :month || '-%' ORDER BY date")
    suspend fun getEventDatesForMonth(year: String, month: String): List<String>

}
