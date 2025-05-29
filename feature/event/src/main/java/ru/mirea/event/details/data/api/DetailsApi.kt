package ru.mirea.event.details.data.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import ru.mirea.core.network.SplitClient
import javax.inject.Inject

class DetailsApi @Inject constructor(
    @SplitClient private val client: HttpClient,
) {
    suspend fun getDebts(eventId: Int): List<DetailsDebtDto> {
        return client.get("event/$eventId/debts").body()
    }
} 