package ru.mirea.expense.data

import ru.mirea.expense.data.api.ExpenseApi
import ru.mirea.expense.data.models.TransactionRequestDto
import ru.mirea.expense.data.models.toDomain
import ru.mirea.expense.domain.model.EventUser
import ru.mirea.expense.domain.repository.ExpenseRepository
import javax.inject.Inject

class ExpenseRepositoryImpl @Inject constructor(
    private val api: ExpenseApi,
) : ExpenseRepository {
    override suspend fun getEventUsers(eventId: Int): Result<List<EventUser>> {
        return kotlin.runCatching {
            api.getEventUsers(eventId).map { it.toDomain() }
        }
    }

    override suspend fun saveTransaction(eventId: Int, body: TransactionRequestDto): Result<Unit> {
        return kotlin.runCatching {
            api.saveTransaction(eventId, body)
        }
    }
} 