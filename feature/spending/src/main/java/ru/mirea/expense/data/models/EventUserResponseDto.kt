package ru.mirea.expense.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventUserResponseDto(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("is_dummy")
    val isDummy: Boolean,
    @SerialName("profile")
    val profile: ProfileDto? = null,
)

@Serializable
data class ProfileDto(
    @SerialName("user_id")
    val userId: Int,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("name")
    val name: String?,
    @SerialName("photo")
    val photo: String?,
) 