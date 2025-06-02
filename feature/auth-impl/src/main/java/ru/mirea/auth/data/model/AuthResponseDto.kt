package ru.mirea.auth.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponseDto(
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("expires_at")
    val expiresAt: String,
    @SerialName("refresh_token")
    val refreshToken: String,
    @SerialName("user")
    val user: User
) {
    @Serializable
    data class User(
        @SerialName("email")
        val email: String,
        @SerialName("id")
        val id: Long,
        @SerialName("name")
        val name: String? = null,
        @SerialName("nickname")
        val nickname: String,
        @SerialName("roles")
        val roles: List<String>
    )
}