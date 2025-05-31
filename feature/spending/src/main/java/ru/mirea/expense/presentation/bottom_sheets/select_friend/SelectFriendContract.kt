package ru.mirea.expense.presentation.bottom_sheets.select_friend

import ru.mirea.expense.domain.model.EventUser

// State для выбора друга

data class SelectFriendState(
    val friends: List<EventUser> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)

// Event для выбора друга
sealed interface SelectFriendEvent {
    data class Load(val eventId: Int) : SelectFriendEvent
} 