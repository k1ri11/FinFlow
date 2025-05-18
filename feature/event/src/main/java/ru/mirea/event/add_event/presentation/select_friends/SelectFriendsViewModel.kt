package ru.mirea.event.add_event.presentation.select_friends

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.mirea.core.util.BaseViewModel
import ru.mirea.core.util.Const.PAGE_SIZE
import ru.mirea.event.add_event.domain.models.Members
import ru.mirea.feature.friends.data.repository.FriendsPagingSource
import javax.inject.Inject

@HiltViewModel
class SelectFriendsViewModel @Inject constructor(
    private val friendsPagingSource: FriendsPagingSource,
) :
    BaseViewModel<SelectFriendsState, SelectFriendsEvent, SelectFriendsEffect>(SelectFriendsState()) {

    val friendsPagingData = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { friendsPagingSource }
    ).flow.cachedIn(viewModelScope)

    override fun event(event: SelectFriendsEvent) {
        when (event) {
            is SelectFriendsEvent.AddTempFriend -> {
                updateState { it.copy(showNewFriendDialog = true, newFriendName = "") }
            }

            is SelectFriendsEvent.NewTempFriendNameChanged -> {
                updateState { it.copy(newFriendName = event.name) }
            }

            is SelectFriendsEvent.ConfirmAddTempFriend -> {
                val name = state.value.newFriendName.trim()
                if (name.isNotEmpty()) {
                    updateState {
                        it.copy(
                            tempFriends = it.tempFriends + TempFriend(name),
                            showNewFriendDialog = false,
                            newFriendName = ""
                        )
                    }
                } else {
                    updateState { it.copy(showNewFriendDialog = false, newFriendName = "") }
                }
            }

            is SelectFriendsEvent.CancelNewFriendDialog -> {
                updateState { it.copy(showNewFriendDialog = false, newFriendName = "") }
            }

            is SelectFriendsEvent.EditTempFriend -> {
                updateState {
                    it.copy(
                        showEditDialog = true,
                        editDialogIndex = event.index,
                        editDialogName = it.tempFriends.getOrNull(event.index)?.name.orEmpty()
                    )
                }
            }

            is SelectFriendsEvent.TempFriendNameChanged -> {
                val updated = state.value.tempFriends.toMutableList().apply {
                    if (event.index in indices) this[event.index] = TempFriend(event.name)
                }
                updateState {
                    it.copy(
                        tempFriends = updated,
                        showEditDialog = false,
                        editDialogIndex = null,
                        editDialogName = ""
                    )
                }
            }

            is SelectFriendsEvent.SelectFriend -> {
                updateState { it.copy(selectedFriends = it.selectedFriends + event.friend) }
            }

            is SelectFriendsEvent.UnselectFriend -> {
                updateState { it.copy(selectedFriends = it.selectedFriends - event.friend) }
            }

            is SelectFriendsEvent.Save -> {
                val members = Members(
                    friends = state.value.selectedFriends.toList(),
                    dummiesNames = state.value.tempFriends.map { it.name }
                )
                emitEffect(SelectFriendsEffect.Saved(members))
            }

            is SelectFriendsEvent.CancelEditDialog -> {
                updateState {
                    it.copy(
                        showEditDialog = false,
                        editDialogIndex = null,
                        editDialogName = ""
                    )
                }
            }

            is SelectFriendsEvent.RemoveTempFriend -> {
                val updated = state.value.tempFriends.toMutableList().apply {
                    if (event.index in indices) removeAt(event.index)
                }
                updateState { it.copy(tempFriends = updated) }
            }
        }
    }
} 