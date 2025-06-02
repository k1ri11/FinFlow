package ru.mirea.feature.friends.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
{
"page": 1,
"page_size": 20,
"total": 1,
"objects": [
{
"user_id": 7,
"photo_id": "00000000-0000-0000-0000-000000000000",
"name": "Kirill2",
"status": "accepted"
}
]
}
 */
@Serializable
data class FriendsDto(
    @SerialName("objects") val friends: List<FriendDto>,
    @SerialName("owed") val owed: Int = 300,
    @SerialName("payed") val payed: Int = 150,
    @SerialName("page") val page: Int,
    @SerialName("page_size") val pageSize: Int,
    @SerialName("total") val total: Int,
) {
    @Serializable
    data class FriendDto(
        @SerialName("name") val name: String,
        @SerialName("photo_id") val photoId: String,
        @SerialName("status") val status: String,
        @SerialName("user_id") val userId: Int,
        @SerialName("owe") val owe: Int = 100,
    )
}