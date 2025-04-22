package ru.mirea.feature.friends.presentation


data class FriendsState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val owed: Int = 600,
    val payed: Int = 300,
)

sealed interface FriendsEvent {
}

sealed interface FriendsEffect {
    data class ShowError(val message: String) : FriendsEffect
}