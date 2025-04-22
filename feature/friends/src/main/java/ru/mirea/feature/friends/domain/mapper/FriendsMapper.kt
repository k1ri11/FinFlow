package ru.mirea.feature.friends.domain.mapper

import ru.mirea.feature.friends.data.model.FriendsDto
import ru.mirea.feature.friends.data.model.FriendsDto.FriendDto
import ru.mirea.feature.friends.domain.model.Friend
import ru.mirea.feature.friends.domain.model.Friends

fun FriendsDto.toDomain() = Friends(
    owed = owed,
    payed = payed,
    friends = friends.map { it.toDomain() },
    page = page,
    pageSize = pageSize,
    total = total,
)

fun FriendDto.toDomain() = Friend(
    id = userId,
    icon = photoId,
    name = name,
    owe = owe,
    isPositive = owe > 0,
    status = status
) 