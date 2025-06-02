package ru.mirea.feature.friends.data.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import ru.mirea.core.network.IdClient
import ru.mirea.feature.friends.data.model.FriendsDto
import javax.inject.Inject

class FriendsApi @Inject constructor(
    @IdClient private val client: HttpClient,
) {
    suspend fun getFriends(nickName: String, pageSize: Int, page: Int): FriendsDto {
        return client.get("users/$nickName/friends") {
            parameter("page_size", pageSize)
            parameter("page", page)
        }.body()
    }
} 