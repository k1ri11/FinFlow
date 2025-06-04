package ru.mirea.event.add_event.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventCreateRequestDto(
    @SerialName("name") val name: String,
    @SerialName("description") val description: String,
    @SerialName("category_id") val categoryId: Int,
    @SerialName("members") val members: MembersBodyDto,
)

@Serializable
data class MembersBodyDto(
    @SerialName("user_ids") val userIds: List<Int> = emptyList(),
    @SerialName("dummies_names") val dummiesNames: List<String> = emptyList(),
)