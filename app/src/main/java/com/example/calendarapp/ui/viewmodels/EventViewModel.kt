package com.example.calendarapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calendarapp.data.model.Event
import com.example.calendarapp.data.model.EventType
import com.example.calendarapp.data.repository.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val repository: EventRepository,
) : ViewModel() {

    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events: StateFlow<List<Event>> get() = _events

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> get() = _loading

    fun fetchEvents(date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true  // Start loading
            try {
                launch{

                }
                launch{

                }
                repository.getEventsByDate(date).collect{
                    _events.value = it
                    _loading.value = false
                }
            } catch (e: Exception) {
                Log.e("EventViewModel", "Error fetching events: ${e.message}")
                _loading.value = false
            }
        }
    }

    suspend fun returnFetchEvents(date: String): List<Event> {
        return try {
            repository.getEventsByDate(date).first().also {
                _events.value = it
            }
        } catch (e: Exception) {
            Log.e("EventViewModel", "Error fetching events: ${e.message}")
            emptyList()
        }
    }

    fun addEvent(
        date: String,
        description: String,
        type: EventType,
        startTime: String?,
        endTime: String?,
    ) {
        viewModelScope.launch {
            val event = Event(
                date = date,
                title = description,
                type = type,
                startTime = startTime,
                endTime = endTime
            )
            repository.addEvent(event)
        }
    }

    fun updateEvent(event: Event) {
        viewModelScope.launch {
            repository.updateEvent(event)
            returnFetchEvents(event.date) // Refresh events after update
        }
    }

    fun deleteEvent(event: Event) {
        viewModelScope.launch {
            repository.deleteEvent(event)
        }
    }

    private val _eventDates = MutableStateFlow<Set<Int>>(emptySet())
    val eventDates: StateFlow<Set<Int>> = _eventDates

    fun fetchEventDatesForMonth(year: Int, month: Int) {
        viewModelScope.launch {
            _eventDates.value = repository.getEventDatesForMonth(year, month)
        }
    }

}
