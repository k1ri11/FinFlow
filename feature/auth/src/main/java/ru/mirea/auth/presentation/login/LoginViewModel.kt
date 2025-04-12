package ru.mirea.auth.presentation.login

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.mirea.auth.domain.AuthRepository
import ru.mirea.core.util.AppDispatchers
import ru.mirea.core.util.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo: AuthRepository,
    private val dispatchers: AppDispatchers,
) : BaseViewModel<LoginState, LoginEvent, LoginEffect>(LoginState()) {

    override fun event(event: LoginEvent) {
        when (event) {
            is LoginEvent.EmailChanged -> updateEmail(event)

            is LoginEvent.PasswordChanged -> updatePassword(event)

            LoginEvent.Submit -> handleSubmit()

        }
    }

    private fun updatePassword(event: LoginEvent.PasswordChanged) {
        updateState {
            it.copy(password = event.password, buttonEnabled = isButtonEnabled())
        }
    }

    private fun updateEmail(event: LoginEvent.EmailChanged) {
        updateState {
            it.copy(
                email = event.email,
                isEmailValid = isEmailValid(event.email),
                buttonEnabled = isButtonEnabled()
            )
        }
    }

    private fun isButtonEnabled(): Boolean {
        val currentState = state.value
        return !currentState.isLoading && currentState.isEmailValid && currentState.password.length >= 6
    }

    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isFormValid(): Boolean {
        val currentState = state.value
        return currentState.isEmailValid && currentState.password.length >= 6
    }

    private fun handleSubmit() {
        val currentState = state.value
        if (currentState.isLoading || !isFormValid()) return

        viewModelScope.launch(dispatchers.io) {
            updateState { it.copy(isLoading = true, error = null) }

            repo.login(email = currentState.email, password = currentState.password)
                .onSuccess {
                    updateState { it.copy(isLoading = false) }
                    emitEffect(LoginEffect.NavigateToMain)
                }
                .onFailure { throwable ->
                    updateState { it.copy(isLoading = false, error = throwable.message) }
                    emitEffect(LoginEffect.ShowError(throwable.message ?: "Неизвестная ошибка"))
                }

        }
    }
}