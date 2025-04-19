package ru.mirea.feature.friends.domain.mapper

import ru.mirea.feature.friends.data.api.model.FriendDto
import ru.mirea.feature.friends.data.api.model.FriendsDto
import ru.mirea.feature.friends.domain.model.Friend
import ru.mirea.feature.friends.domain.model.Friends

fun FriendsDto.toDomain() = Friends(
    owed = owed,
    payed = payed,
    friends = friends.map { it.toDomain() }
)

fun FriendDto.toDomain() = Friend(
    icon = icon,
    name = name,
    owe = owe,
    isPositive = owe > 0
) 