package ru.mirea.profile.domain

import ru.mirea.profile.data.ProfileApi
import ru.mirea.profile.domain.model.Profile
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val api: ProfileApi,
) {
    suspend fun getProfile(nickName: String): Result<Profile> = runCatching {
        api.getProfile(nickName).toDomain()
    }

    suspend fun updateProfile(profile: Profile, changedFields: Set<String>): Result<Profile> =
        runCatching {
            api.updateProfile(profile.toPatchDto(changedFields)).toDomain()
        }
} 