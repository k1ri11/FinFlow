package ru.mirea.event.details.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventActivityDto(
    @SerialName("activity_id") val activityId: Int,
    val description: String,
    @SerialName("icon_id") val iconId: String,
    val datetime: String,
)

@Serializable
data class ActivitiesResponseDto(
    val activities: List<EventActivityDto>,
)