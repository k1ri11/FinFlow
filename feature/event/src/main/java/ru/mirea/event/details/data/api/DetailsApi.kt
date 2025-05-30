package ru.mirea.event.details.data.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import ru.mirea.core.network.SplitClient
import ru.mirea.event.details.data.model.DetailsDebtDto
import ru.mirea.event.details.data.model.OptimizedDebtDto
import ru.mirea.event.details.data.model.TransactionDto
import ru.mirea.event.details.data.model.TransactionsResponseDto
import javax.inject.Inject


class DetailsApi @Inject constructor(
    @SplitClient private val client: HttpClient,
) {
    suspend fun getDebts(eventId: Int): List<DetailsDebtDto> {
        return client.get("event/$eventId/debts").body()
    }

    suspend fun getTransactions(eventId: Int): List<TransactionDto> {
        return client.get("event/$eventId/transaction").body<TransactionsResponseDto>().transactions
    }

    suspend fun getOptimizedDebts(eventId: Int): List<OptimizedDebtDto> {
        return client.post("event/$eventId/optimized-debts").body()
    }
} 