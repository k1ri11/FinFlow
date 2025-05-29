package ru.mirea.event.details.data.repository

import ru.mirea.event.details.data.api.DetailsApi
import ru.mirea.event.details.domain.model.DetailsDebt
import ru.mirea.event.details.domain.toDomain
import javax.inject.Inject

class DetailsRepository @Inject constructor(
    private val api: DetailsApi,
) {
    suspend fun getDebts(eventId: Int): Result<List<DetailsDebt>> = runCatching {
        api.getDebts(eventId).map { it.toDomain() }
    }
} 