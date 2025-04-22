package ru.mirea.feature.friends.data.repository

import ru.mirea.core.service.UserService
import ru.mirea.feature.friends.data.api.FriendsApi
import javax.inject.Inject

class FriendsRepository @Inject constructor(
    private val api: FriendsApi,
    private val userService: UserService,
) {

}