package ru.mirea.feature.friends.presentation

import ru.mirea.feature.friends.domain.model.Friend

data class FriendsState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val owed: Int = 0,
    val payed: Int = 0,
    val friends: List<Friend> = emptyList(),
)

sealed interface FriendsEvent {
    data object LoadFriends : FriendsEvent
}

sealed interface FriendsEffect {
    data class ShowError(val message: String) : FriendsEffect
    data class NavigateToFriendDetails(val friend: Friend) : FriendsEffect
} 