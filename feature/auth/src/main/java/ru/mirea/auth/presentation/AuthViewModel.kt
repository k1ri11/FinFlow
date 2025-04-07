package ru.mirea.auth.presentation

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.mirea.auth.domain.AuthRepository
import ru.mirea.core.util.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : BaseViewModel<AuthState, AuthEvent, AuthEffect>(AuthState()) {

    override fun event(event: AuthEvent) {
        when (event) {
            is AuthEvent.EmailChanged -> {
                updateState {
                    it.copy(
                        email = event.email,
                        isEmailValid = isEmailValid(event.email)
                    )
                }
            }

            is AuthEvent.PasswordChanged -> updateState { it.copy(password = event.password) }
            is AuthEvent.NameChanged -> updateState { it.copy(name = event.name) }
            AuthEvent.ToggleMode -> updateState { it.copy(isLoginMode = !it.isLoginMode) }
            AuthEvent.Submit -> handleSubmit()
            AuthEvent.TogglePasswordVisibility -> updateState {
                it.copy(isPasswordVisible = !it.isPasswordVisible)
            }
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isFormValid(): Boolean {
        val currentState = state.value
        return currentState.isEmailValid &&
                currentState.password.length >= 6 &&
                (!currentState.isLoginMode && currentState.name.isNotBlank() || currentState.isLoginMode)
    }

    private fun handleSubmit() {
        val currentState = state.value
        if (currentState.isLoading || !isFormValid()) return

        viewModelScope.launch {
            updateState { it.copy(isLoading = true, error = null) }

            val result = if (currentState.isLoginMode) {
                repository.login(currentState.email, currentState.password)
            } else {
                repository.register(currentState.email, currentState.password, currentState.name)
            }

            result.fold(
                onSuccess = { authResult ->
                    updateState {
                        it.copy(
                            isLoading = false,
                            isLoggedIn = true,
                            user = authResult.user
                        )
                    }
                    emitEffect(AuthEffect.NavigateToMain)
                },
                onFailure = { error ->
                    updateState { it.copy(isLoading = false, error = error.message) }
                    emitEffect(AuthEffect.ShowError(error.message ?: "Неизвестная ошибка"))
                }
            )
        }
    }
} 