package ru.mirea.event.event_list.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import ru.mirea.core.network.SplitClient
import javax.inject.Inject

class EventsApi @Inject constructor(
    @SplitClient private val client: HttpClient,
) {
    suspend fun getEvents(): EventsListResponseDto {
        return client.get("event").body()
    }
}