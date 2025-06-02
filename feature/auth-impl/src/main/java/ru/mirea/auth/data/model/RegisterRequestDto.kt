package ru.mirea.auth.data.model

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequestDto(
    val email: String,
    val password: String,
    val nickname: String
) 