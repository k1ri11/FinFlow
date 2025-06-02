package ru.mirea.feature.friends.domain.model

import ru.mirea.friends_api.Friend


data class Friends(
    val owed: Int,
    val payed: Int,
    val friends: List<Friend>,
    val page: Int,
    val pageSize: Int,
    val total: Int,
)