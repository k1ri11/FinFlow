package ru.mirea.auth.presentation.login

import ru.mirea.auth.domain.model.User

data class LoginState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val user: User? = null,
    val email: String = "",
    val password: String = "",
    val isEmailValid: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val buttonEnabled: Boolean = false,
)

sealed interface LoginEvent {
    data class EmailChanged(val email: String) : LoginEvent
    data class PasswordChanged(val password: String) : LoginEvent
    data object Submit : LoginEvent
}

sealed interface LoginEffect {
    data class ShowError(val message: String) : LoginEffect
    data object NavigateToMain : LoginEffect
} 