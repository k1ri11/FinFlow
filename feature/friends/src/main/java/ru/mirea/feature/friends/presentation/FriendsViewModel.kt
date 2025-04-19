package ru.mirea.feature.friends.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.mirea.core.util.AppDispatchers
import ru.mirea.core.util.BaseViewModel
import ru.mirea.feature.friends.data.repository.FriendsRepository
import javax.inject.Inject

@HiltViewModel
class FriendsViewModel @Inject constructor(
    private val repository: FriendsRepository,
    private val dispatchers: AppDispatchers,
) : BaseViewModel<FriendsState, FriendsEvent, FriendsEffect>(FriendsState()) {

    override fun event(event: FriendsEvent) {
        when (event) {
            FriendsEvent.LoadFriends -> loadFriends()
        }
    }

    private fun loadFriends() {
        viewModelScope.launch(dispatchers.io) {
            updateState { it.copy(isLoading = true, error = null) }

            repository.getFriends()
                .onSuccess { friends ->
                    updateState {
                        it.copy(
                            isLoading = false,
                            owed = friends.owed,
                            payed = friends.payed,
                            friends = friends.friends
                        )
                    }
                }
                .onFailure { error ->
                    updateState { it.copy(isLoading = false, error = error.message) }
                    emitEffect(FriendsEffect.ShowError(error.message ?: "Неизвестная ошибка"))
                }
        }
    }
} 