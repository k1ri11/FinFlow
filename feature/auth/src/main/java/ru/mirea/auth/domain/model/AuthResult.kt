package ru.mirea.auth.domain.model

data class AuthResult(
    val user: User
)

data class User(
    val id: Long,
    val email: String,
    val name: String? = null
) 