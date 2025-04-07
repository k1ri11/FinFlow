package ru.mirea.auth.presentation

import ru.mirea.auth.domain.model.User

data class AuthState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isLoggedIn: Boolean = false,
    val user: User? = null,
    val email: String = "",
    val password: String = "",
    val name: String = "",
    val isLoginMode: Boolean = true,
    val isEmailValid: Boolean = false,
    val isPasswordVisible: Boolean = false
)

sealed interface AuthEvent {
    data class EmailChanged(val email: String) : AuthEvent
    data class PasswordChanged(val password: String) : AuthEvent
    data class NameChanged(val name: String) : AuthEvent
    data object ToggleMode : AuthEvent
    data object Submit : AuthEvent
    data object TogglePasswordVisibility : AuthEvent
}

sealed interface AuthEffect {
    data class ShowError(val message: String) : AuthEffect
    data object NavigateToMain : AuthEffect
} 