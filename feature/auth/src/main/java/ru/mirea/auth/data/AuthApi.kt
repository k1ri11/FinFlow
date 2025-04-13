package ru.mirea.auth.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import ru.mirea.auth.data.model.AuthRequestDto
import ru.mirea.auth.data.model.AuthResponseDto
import ru.mirea.auth.data.model.RegisterRequestDto
import ru.mirea.core.network.AuthClient
import javax.inject.Inject

class AuthApi @Inject constructor(
    @AuthClient private val networkClient: HttpClient,
) {
    suspend fun login(email: String, password: String): AuthResponseDto {
        return networkClient.post("auth/login") {
            setBody(AuthRequestDto(login = email, password = password))
        }.body()
    }

    suspend fun register(email: String, password: String, name: String): AuthResponseDto {
        return networkClient.post("auth/register") {
            setBody(RegisterRequestDto(email = email, password = password, nickname = name))
        }.body()
    }
} 