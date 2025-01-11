package com.example.calendarapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.calendarapp.data.dao.EventDao
import com.example.calendarapp.data.model.Event

@Database(entities = [Event::class], version = 1)
abstract class MyDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao
}
