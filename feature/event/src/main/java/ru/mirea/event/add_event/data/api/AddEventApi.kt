package ru.mirea.event.add_event.data.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import ru.mirea.core.network.SplitClient
import ru.mirea.event.add_event.data.models.CategoryListResponseDto
import ru.mirea.event.add_event.data.models.EventCreateRequestDto
import javax.inject.Inject

class AddEventApi @Inject constructor(
    @SplitClient private val networkClient: HttpClient,
) {
    suspend fun getEventCategories(): CategoryListResponseDto {
        return networkClient.get("category?category_type=event").body()
    }

    suspend fun createEvent(body: EventCreateRequestDto) {
        networkClient.post("event") {
            setBody(body)
        }
    }

    suspend fun syncUsers(userIds: List<Int>) {
        networkClient.post("user/sync") {
            setBody(mapOf("user_ids" to userIds))
        }
    }


} 