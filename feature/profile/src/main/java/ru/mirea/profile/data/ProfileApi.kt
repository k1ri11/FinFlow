package ru.mirea.profile.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.setBody
import ru.mirea.core.network.IdClient
import ru.mirea.profile.data.model.ProfileDto
import ru.mirea.profile.data.model.ProfilePatchDto
import javax.inject.Inject

class ProfileApi @Inject constructor(
    @IdClient private val networkClient: HttpClient,
) {
    suspend fun getProfile(nickName: String): ProfileDto {
        return networkClient.get("users/$nickName").body()
    }

    suspend fun updateProfile(profilePatch: ProfilePatchDto): ProfileDto {
        return networkClient.patch("users/me") {
            setBody(profilePatch)
        }.body()
    }
} 