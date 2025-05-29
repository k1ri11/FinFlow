package ru.mirea.event.details.domain.model

data class EventActivity(
    val activityId: Int,
    val description: String,
    val iconId: String,
    val datetime: String,
)