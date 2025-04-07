package ru.mirea.core.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * MVI core interface
 *
 *  @see [this](https://proandroiddev.com/migrate-from-mvvm-to-mvi-f938c27c214f)
 */

// State - Event - Effect

interface UnidirectionalViewModel<STATE, EVENT, EFFECT> {
    val state: StateFlow<STATE>
    val effect: SharedFlow<EFFECT>
    fun event(event: EVENT)
}

data class UiHandler<STATE, EVENT, EFFECT>(
    val state: STATE,
    val dispatch: (EVENT) -> Unit,
    val effectFlow: SharedFlow<EFFECT>,
)

@Composable
inline fun <reified STATE, EVENT, EFFECT> useBy(
    viewModel: UnidirectionalViewModel<STATE, EVENT, EFFECT>,
): UiHandler<STATE, EVENT, EFFECT> {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val dispatch: (EVENT) -> Unit = remember {
        { event ->
            viewModel.event(event)
        }
    }

    return UiHandler(
        state = state,
        effectFlow = viewModel.effect,
        dispatch = dispatch,
    )
}

@Suppress("ComposableNaming")
@Composable
fun <T> SharedFlow<T>.collectInLaunchedEffect(function: suspend (value: T) -> Unit) {
    val sharedFlow = this
    LaunchedEffect(key1 = sharedFlow) {
        sharedFlow.collectLatest(function)
    }
}

// State - Event

interface UnidirectionalVM<STATE, EVENT> {
    val state: StateFlow<STATE>
    fun event(event: EVENT)
}

@Composable
inline fun <reified STATE, EVENT> useBy(
    viewModel: UnidirectionalVM<STATE, EVENT>,
): UiHandler<STATE, EVENT, Nothing> {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val dispatch: (EVENT) -> Unit = { event ->
        viewModel.event(event)
    }
    return UiHandler(
        state = state,
        dispatch = dispatch,
        effectFlow = MutableSharedFlow()
    )
}

/**
 * An abstract base ViewModel that implements [UnidirectionalViewModel] and provides basic
 * functionality for managing state and effects.
 *
 * @param STATE The type that represents the state of the ViewModel. For example, the UI state of a screen can be
 * represented as a state.
 * @param EVENT The type that represents user actions that can occur and should be handled by the ViewModel. For
 * example, a button click can be represented as an event.
 * @param EFFECT The  type that represents side-effects that can occur in response to the state changes. For example,
 * a navigation event can be represented as an effect.
 *
 * @property initialState The initial [STATE] of the ViewModel.
 */
abstract class BaseViewModel<STATE, EVENT, EFFECT>(
    initialState: STATE,
) : ViewModel(),
    UnidirectionalViewModel<STATE, EVENT, EFFECT> {

    private val _state = MutableStateFlow(initialState)
    override val state: StateFlow<STATE> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<EFFECT>()
    override val effect: SharedFlow<EFFECT> = _effect.asSharedFlow()

    private val handledOneTimeEvents = mutableSetOf<EVENT>()

    /**
     * Updates the [STATE] of the ViewModel.
     * @param update A function that takes the current [STATE] and produces a new [STATE].
     */
    protected fun updateState(update: (STATE) -> STATE) {
        _state.update(update)
    }

    /**
     * Emits a side effect.
     * @param effect The [EFFECT] to emit.
     */
    protected fun emitEffect(effect: EFFECT) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }

    /**
     * Ensures that one-time events are only handled once.
     *
     * When you can't ensure that an event is only sent once, but you want the event to only be handled once, call this
     * method. It will ensure [block] is only executed the first time this function is called. Subsequent calls with an
     * [event] argument equal to that of a previous invocation will not execute [block].
     *
     * Multiple one-time events are supported.
     */
    protected fun handleOneTimeEvent(event: EVENT, block: () -> Unit) {
        if (event !in handledOneTimeEvents) {
            handledOneTimeEvents.add(event)
            block()
        }
    }
}