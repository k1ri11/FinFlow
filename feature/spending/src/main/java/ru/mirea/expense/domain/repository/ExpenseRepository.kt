package ru.mirea.expense.domain.repository

import ru.mirea.expense.data.models.TransactionRequestDto
import ru.mirea.expense.domain.model.EventUser

interface ExpenseRepository {
    suspend fun getEventUsers(eventId: Int): Result<List<EventUser>>
    suspend fun saveTransaction(eventId: Int, body: TransactionRequestDto): Result<Unit>
} 