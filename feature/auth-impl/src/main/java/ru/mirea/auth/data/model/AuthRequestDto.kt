package ru.mirea.auth.data.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequestDto(
    val login: String,
    val password: String
) 