package ru.mirea.event.add_event.domain

import ru.mirea.event.add_event.data.AddEventApi
import ru.mirea.event.add_event.domain.models.Category
import javax.inject.Inject

class AddEventRepository @Inject constructor(
    private val api: AddEventApi,
) {
    suspend fun getEventCategories(): Result<List<Category>> = runCatching {
        api.getEventCategories().categories.map { it.toDomain() }
    }
} 