package ru.mirea.event.add_event.domain.repository

import ru.mirea.core.service.UserService
import ru.mirea.event.add_event.data.api.AddEventApi
import ru.mirea.event.add_event.data.models.EventCreateRequestDto
import ru.mirea.event.add_event.data.models.MembersBodyDto
import ru.mirea.event.add_event.domain.models.Category
import ru.mirea.event.add_event.domain.models.Members
import ru.mirea.event.add_event.domain.toDomain
import javax.inject.Inject

class AddEventRepository @Inject constructor(
    private val api: AddEventApi,
    private val userService: UserService,

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
        val currentUserId = userService.getCurrentUserId()
        val userIds = members.friends.map { it.id }
        val dummiesNames = members.dummiesNames
        api.syncUsers(userIds)
        val body = EventCreateRequestDto(
            name = name,
            description = description,
            categoryId = categoryId,
            members = MembersBodyDto(
                userIds = userIds.toList(),
                dummiesNames = dummiesNames
            )
        )
        api.createEvent(body)
    }

} 