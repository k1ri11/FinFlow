package ru.mirea.event.add_event.presentation.select_friends

import ru.mirea.event.add_event.domain.models.Members
import ru.mirea.feature.friends.domain.model.Friend

data class TempFriend(val name: String)

data class SelectFriendsState(
    val tempFriends: List<TempFriend> = emptyList(),
    val selectedFriends: Set<Friend> = emptySet(),
    val showEditDialog: Boolean = false,
    val editDialogIndex: Int? = null,
    val editDialogName: String = "",
    val showNewFriendDialog: Boolean = false,
    val newFriendName: String = "",
)

sealed interface SelectFriendsEvent {
    data object AddTempFriend : SelectFriendsEvent
    data class EditTempFriend(val index: Int) : SelectFriendsEvent
    data class TempFriendNameChanged(val index: Int, val name: String) : SelectFriendsEvent
    data class NewTempFriendNameChanged(val name: String) : SelectFriendsEvent
    data object ConfirmAddTempFriend : SelectFriendsEvent
    data class SelectFriend(val friend: Friend) : SelectFriendsEvent
    data class UnselectFriend(val friend: Friend) : SelectFriendsEvent
    data object Save : SelectFriendsEvent
    data object CancelEditDialog : SelectFriendsEvent
    data object CancelNewFriendDialog : SelectFriendsEvent
    data class RemoveTempFriend(val index: Int) : SelectFriendsEvent
}

sealed interface SelectFriendsEffect {
    data class Saved(val members: Members) : SelectFriendsEffect
}