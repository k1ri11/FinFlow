package ru.mirea.event.event_list.domain

data class Event(
    val id: Int,
    val name: String,
    val description: String,
    val categoryId: Int? = null,
    val iconId: String? = null,
    val balance: Int? = null,
    val isPositive: Boolean? = null,
)
