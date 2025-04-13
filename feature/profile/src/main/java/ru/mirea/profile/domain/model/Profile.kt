package ru.mirea.profile.domain.model

data class Profile(
    val id: Long? = null,
    val email: String? = null,
    val nickname: String? = null,
    val phone: String? = null,
    val name: String? = null,
    val birthDate: String? = null,
    val avatarUrl: String? = null,
) 