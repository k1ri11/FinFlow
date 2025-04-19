package ru.mirea.feature.friends.data.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import ru.mirea.core.network.IdClient
import ru.mirea.feature.friends.data.api.model.FriendsDto
import javax.inject.Inject

class FriendsApi @Inject constructor(
    //todo потом исправить IdClient на нужный
    @IdClient private val client: HttpClient,
) {
    suspend fun getFriends(): FriendsDto = client.get("friends").body()
} 