package ru.mirea.event.event_list.domain

data class Event(
    val id: Int,
    val name: String,
    val category: String,
    val photoId: String,
    val balance: Int?,
    val isPositive: Boolean,
)
