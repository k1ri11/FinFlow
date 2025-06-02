package ru.mirea.friends_api

data class Friend(
    val id: Int,
    val icon: String,
    val name: String,
    val owe: Int,
    val isPositive: Boolean,
    val status: String,
)