package ru.mirea.event.add_event.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import ru.mirea.core.network.IdClient
import javax.inject.Inject

class AddEventApi @Inject constructor(
    @IdClient private val networkClient: HttpClient,
) {
    suspend fun getEventCategories(): CategoryListResponseDto {
        return networkClient.get("/api/v1/category?category_type=event").body()
    }
} 