package com.example.calendarapp.di

import android.app.Application
import androidx.room.Room
import com.example.calendarapp.data.dao.EventDao
import com.example.calendarapp.data.database.MyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): MyDatabase {
        return Room.databaseBuilder(app, MyDatabase::class.java, "event_db")
            .build()
    }

    @Provides
    @Singleton
    fun provideEventDao(database: MyDatabase): EventDao {
        return database.eventDao()
    }
}
