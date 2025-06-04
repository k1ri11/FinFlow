package ru.mirea.event.add_event.domain.repository

import ru.mirea.event.add_event.data.api.AddEventApi
import ru.mirea.event.add_event.data.models.EventCreateRequestDto
import ru.mirea.event.add_event.data.models.MembersBodyDto
import ru.mirea.event.add_event.domain.models.Category
import ru.mirea.event.add_event.domain.models.Members
import ru.mirea.event.add_event.domain.toDomain
import javax.inject.Inject

class AddEventRepository @Inject constructor(
    private val api: AddEventApi,
) {
    suspend fun getEventCategories(): Result<List<Category>> = runCatching {
        api.getEventCategories().categories.map { it.toDomain() }
    }

    suspend fun createEvent(
        name: String,
        description: String,
        categoryId: Int,
        members: Members,
    ): Result<Unit> = runCatching {
        val userIds = members.friends.map { it.id }
        val dummiesNames = members.dummiesNames
        val body = EventCreateRequestDto(
            name = name,
            description = description,
            categoryId = categoryId,
            members = MembersBodyDto(
                userIds = userIds,
                dummiesNames = dummiesNames
            )
        )
        api.createEvent(body)
    }

    suspend fun syncUsers(userIds: List<Int>) = runCatching { api.syncUsers(userIds) }

} 