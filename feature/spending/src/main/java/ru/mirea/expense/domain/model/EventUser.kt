package ru.mirea.expense.domain.model

data class EventUser(
    val id: Int,
    val name: String,
    val isDummy: Boolean,
    val profile: UserProfile,
)

data class UserProfile(
    val userId: Int,
    val nickname: String,
    val name: String?,
    val photo: String?,
) 