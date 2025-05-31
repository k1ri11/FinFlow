package ru.mirea.expense.data.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import ru.mirea.core.network.SplitClient
import ru.mirea.expense.data.models.EventUserResponseDto
import ru.mirea.expense.data.models.TransactionRequestDto
import javax.inject.Inject


class ExpenseApi @Inject constructor(
    @SplitClient private val networkClient: HttpClient,
) {
    suspend fun getEventUsers(eventId: Int): List<EventUserResponseDto> {
        return networkClient.get("event/$eventId/user").body()
    }

    suspend fun saveTransaction(eventId: Int, body: TransactionRequestDto) {
        networkClient.post("event/$eventId/transaction") {
            setBody(body)
        }
    }
} 