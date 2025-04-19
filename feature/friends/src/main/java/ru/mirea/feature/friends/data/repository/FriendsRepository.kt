package ru.mirea.feature.friends.data.repository

import ru.mirea.feature.friends.data.api.FriendsApi
import ru.mirea.feature.friends.domain.mapper.toDomain
import ru.mirea.feature.friends.domain.model.Friends
import javax.inject.Inject

class FriendsRepository @Inject constructor(
    private val api: FriendsApi,
) {
    suspend fun getFriends(): Result<Friends> = runCatching {
        api.getFriends().toDomain()
    }
} 