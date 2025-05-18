package ru.mirea.feature.friends.domain.model

data class SelectableFriend(
    val icon: String,
    val id: Int,
    val name: String,
    val isSelected: Boolean,
)