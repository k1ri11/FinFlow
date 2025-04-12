package ru.mirea.auth.presentation.register

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.mirea.auth.domain.AuthRepository
import ru.mirea.core.util.AppDispatchers
import ru.mirea.core.util.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repo: AuthRepository,
    private val dispatchers: AppDispatchers,
) : BaseViewModel<RegisterState, RegisterEvent, RegisterEffect>(RegisterState()) {

    override fun event(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.UsernameChanged -> updateUsername(event)

            is RegisterEvent.EmailChanged -> updateEmail(event)

            is RegisterEvent.PasswordChanged -> updatePassword(event)

            RegisterEvent.Submit -> handleSubmit()
        }
    }

    private fun updateUsername(event: RegisterEvent.UsernameChanged) {
        updateState { it.copy(username = event.username, buttonEnabled = isButtonEnabled()) }
    }

    private fun isButtonEnabled(): Boolean {
        val currentState = state.value
        return !currentState.isLoading
                && currentState.isEmailValid
                && currentState.password.length >= 6
                && currentState.username.length >= 3
    }

    private fun updatePassword(event: RegisterEvent.PasswordChanged) {
        updateState { it.copy(password = event.password, buttonEnabled = isButtonEnabled()) }
    }

    private fun updateEmail(event: RegisterEvent.EmailChanged) {
        updateState {
            it.copy(
                email = event.email,
                isEmailValid = isEmailValid(event.email),
                buttonEnabled = isButtonEnabled()
            )
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isFormValid(): Boolean {
        val currentState = state.value
        return currentState.isEmailValid &&
                currentState.password.length >= 6 &&
                currentState.username.isNotBlank()
    }

    private fun handleSubmit() {
        val currentState = state.value
        if (currentState.isLoading || !isFormValid()) return

        viewModelScope.launch(dispatchers.io) {
            updateState { it.copy(isLoading = true, error = null) }
            repo.register(
                email = currentState.email,
                password = currentState.password,
                name = currentState.username
            ).onSuccess {
                updateState { it.copy(isLoading = false) }
                emitEffect(RegisterEffect.NavigateToMain)
            }.onFailure { throwable ->
                updateState { it.copy(isLoading = false, error = throwable.message) }
                emitEffect(RegisterEffect.ShowError(throwable.message ?: "Неизвестная ошибка"))
            }

        }
    }
}