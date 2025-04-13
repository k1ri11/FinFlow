package ru.mirea.profile.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfilePatchDto(
    @SerialName("email")
    val email: String? = null,
    @SerialName("phone")
    val phone: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("nickname")
    val nickname: String? = null,
    @SerialName("birth_date")
    val birthDate: String? = null,
    @SerialName("avatar_url")
    val avatarUrl: String? = null,
) 