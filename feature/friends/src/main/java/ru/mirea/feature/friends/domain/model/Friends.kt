package ru.mirea.feature.friends.domain.model

data class Friends(
    val owed: Int,
    val payed: Int,
    val friends: List<Friend>,
)

data class Friend(
    val icon: String,
    val name: String,
    val owe: Int,
    val isPositive: Boolean,
) 