package ru.mirea.auth_api

data class AuthResult(
    val user: User
)

data class User(
    val id: Int,
    val email: String,
    val name: String? = null,
    val nickname: String,
)