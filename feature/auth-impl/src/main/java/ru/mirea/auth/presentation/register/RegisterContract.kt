package ru.mirea.auth.presentation.register

import ru.mirea.auth_api.User

data class RegisterState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val user: User? = null,
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val isEmailValid: Boolean = false,
    val buttonEnabled: Boolean = false
)

sealed interface RegisterEvent {
    data class UsernameChanged(val username: String) : RegisterEvent
    data class EmailChanged(val email: String) : RegisterEvent
    data class PasswordChanged(val password: String) : RegisterEvent
    data object Submit : RegisterEvent
}

sealed interface RegisterEffect {
    data class ShowError(val message: String) : RegisterEffect
    data object NavigateToMain : RegisterEffect
} 