package ru.mirea.feature.friends.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FriendsDto(
    @SerialName("owed") val owed: Int,
    @SerialName("payed") val payed: Int,
    @SerialName("friends") val friends: List<FriendDto>,
)

@Serializable
data class FriendDto(
    @SerialName("icon") val icon: String,
    @SerialName("name") val name: String,
    @SerialName("owe") val owe: Int,
) 