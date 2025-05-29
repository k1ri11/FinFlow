package ru.mirea.event.details.data.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import ru.mirea.core.network.SplitClient
import ru.mirea.event.details.data.model.ActivitiesResponseDto
import ru.mirea.event.details.data.model.DetailsDebtDto
import ru.mirea.event.details.data.model.EventActivityDto
import ru.mirea.event.details.data.model.TransactionDto
import ru.mirea.event.details.data.model.TransactionsResponseDto
import javax.inject.Inject


class DetailsApi @Inject constructor(
    @SplitClient private val client: HttpClient,
) {
    suspend fun getDebts(eventId: Int): List<DetailsDebtDto> {
        return client.get("event/$eventId/debts").body()
    }
    suspend fun getActivities(eventId: Int): List<EventActivityDto> {
        return client.get("event/$eventId/activity").body<ActivitiesResponseDto>().activities
    }

    suspend fun getTransactions(eventId: Int): List<TransactionDto> {
        return client.get("event/$eventId/transaction").body<TransactionsResponseDto>().transactions
    }
} 