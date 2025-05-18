package ru.mirea.event.event_list.presentation

import ru.mirea.event.event_list.domain.Event

data class EventsState(
    val events: List<Event> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)


sealed interface EventsEvent {
    data object LoadEvents : EventsEvent
}

sealed interface EventsEffect {
    data class ShowError(val message: String) : EventsEffect
}