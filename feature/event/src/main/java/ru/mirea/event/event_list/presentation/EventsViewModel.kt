package ru.mirea.event.event_list.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.mirea.core.util.BaseViewModel
import ru.mirea.event.event_list.domain.EventsRepository
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(
    private val repository: EventsRepository,
) : BaseViewModel<EventsState, EventsEvent, EventsEffect>(EventsState()) {

    override fun event(event: EventsEvent) {
        when (event) {
            is EventsEvent.LoadEvents -> {
                updateState { it.copy(isLoading = true, error = null) }
                viewModelScope.launch {
                    repository.getEvents()
                        .onSuccess { events ->
                            updateState { it.copy(events = events, isLoading = false) }
                        }
                        .onFailure { err ->
                            updateState { it.copy(isLoading = false, error = err.message) }
                            Log.d("aboba", "event: ${err.message}")
                            emitEffect(
                                EventsEffect.ShowError(
                                    err.message ?: "Ошибка загрузки событий"
                                )
                            )
                        }
                }
            }
        }
    }
}