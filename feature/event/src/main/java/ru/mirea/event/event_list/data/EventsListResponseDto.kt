package ru.mirea.event.event_list.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventsListResponseDto(
    @SerialName("events")
    val events: List<EventDto>,
)

@Serializable
data class EventDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("description") val description: String,
    @SerialName("category_id") val categoryId: Int? = null,
)