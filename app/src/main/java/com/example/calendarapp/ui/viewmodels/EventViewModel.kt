package com.example.calendarapp.ui.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calendarapp.data.model.Event
import com.example.calendarapp.data.model.EventType
import com.example.calendarapp.data.repository.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val repository: EventRepository
) : ViewModel() {

    private val _events = mutableStateOf<List<Event>>(emptyList())
    val events: State<List<Event>> = _events

    fun fetchEvents(date: String) {
        viewModelScope.launch {
            _events.value = repository.getEventsByDate(date)
        }
    }

    fun addEvent(date: String, description: String, type: EventType, startTime: String?, endTime: String?) {
        viewModelScope.launch {
            val event = Event(date = date, description = description, type = type, startTime = startTime, endTime = endTime)
            repository.addEvent(event)
            fetchEvents(date) // Refresh events after adding
        }
    }

    fun updateEvent(event: Event) {
        viewModelScope.launch {
            repository.updateEvent(event)
            fetchEvents(event.date) // Refresh events after update
        }
    }
}
