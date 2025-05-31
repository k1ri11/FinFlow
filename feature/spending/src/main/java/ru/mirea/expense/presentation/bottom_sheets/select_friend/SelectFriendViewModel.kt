package ru.mirea.expense.presentation.bottom_sheets.select_friend

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.mirea.core.util.BaseViewModel
import ru.mirea.expense.domain.repository.ExpenseRepository
import javax.inject.Inject

@HiltViewModel
class SelectFriendViewModel @Inject constructor(
    private val repository: ExpenseRepository,
) : BaseViewModel<SelectFriendState, SelectFriendEvent, Nothing>(SelectFriendState()) {

    override fun event(event: SelectFriendEvent) {
        when (event) {
            is SelectFriendEvent.Load -> {
                loadFriends(event.eventId)
            }
        }
    }

    private fun loadFriends(eventId: Int) {
        updateState { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            val result = repository.getEventUsers(eventId)
            result.onSuccess { users ->
                updateState { it.copy(friends = users, isLoading = false) }
            }.onFailure { err ->
                updateState { it.copy(isLoading = false, error = err.message) }
            }
        }
    }
}