package ru.mirea.event.add_event.data.models

import kotlinx.serialization.Serializable

@Serializable
data class EventCreateRequestDto(
    val name: String,
    val description: String,
    val categoryId: Int,
    val members: MembersBodyDto,
)

@Serializable
data class MembersBodyDto(
    val userIds: List<Int> = emptyList(),
    val dummiesNames: List<String> = emptyList(),
)