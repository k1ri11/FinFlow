package ru.mirea.event.event_list.domain

import ru.mirea.event.event_list.data.EventsApi
import javax.inject.Inject

class EventsRepository @Inject constructor(
    private val api: EventsApi,
) {
    suspend fun getEvents(): Result<List<Event>> = runCatching {
        api.getEvents().events.map { it.toDomain() }
    }
}