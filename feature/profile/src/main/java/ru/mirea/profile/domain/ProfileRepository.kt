package ru.mirea.profile.domain

import ru.mirea.core.service.UserService
import ru.mirea.profile.data.ProfileApi
import ru.mirea.profile.domain.model.Profile
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val api: ProfileApi,
    private val userService: UserService,
) {
    suspend fun getProfile(): Result<Profile> = runCatching {
        val userNickname = userService.getCurrentUserNick()
        if (userNickname != null) {
            api.getProfile(userNickname).toDomain()
        } else throw IllegalStateException("Текущей пользователь не установлен")
    }

    suspend fun updateProfile(profile: Profile, changedFields: Set<String>): Result<Profile> =
        runCatching {
            api.updateProfile(profile.toPatchDto(changedFields)).toDomain()
        }
} 