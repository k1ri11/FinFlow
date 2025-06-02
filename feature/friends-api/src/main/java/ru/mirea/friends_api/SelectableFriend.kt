package ru.mirea.friends_api

data class SelectableFriend(
    val icon: String,
    val id: Int,
    val name: String,
    val isSelected: Boolean,
)