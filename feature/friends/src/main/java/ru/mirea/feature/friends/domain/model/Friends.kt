package ru.mirea.feature.friends.domain.model


data class Friends(
    val owed: Int,
    val payed: Int,
    val friends: List<Friend>,
    val page: Int,
    val pageSize: Int,
    val total: Int,
)

data class Friend(
    val id: Int,
    val icon: String,
    val name: String,
    val owe: Int,
    val isPositive: Boolean,
    val status: String,
)