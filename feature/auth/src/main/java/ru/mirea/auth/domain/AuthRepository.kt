package ru.mirea.auth.domain

import kotlinx.coroutines.flow.first
import ru.mirea.auth.data.AuthApi
import ru.mirea.auth.domain.model.AuthResult
import ru.mirea.auth.domain.model.User
import ru.mirea.core.network.TokenManager
import ru.mirea.core.service.UserService
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: AuthApi,
    private val tokenManager: TokenManager,
    private val userService: UserService,
) {

    suspend fun login(email: String, password: String): Result<AuthResult> {
        return runCatching {
            api.login(email, password).let { response ->
                tokenManager.saveTokens(response.accessToken, response.refreshToken)
                AuthResult(
                    user = User(
                        id = response.user.id,
                        email = response.user.email,
                        name = response.user.name,
                        nickname = response.user.nickname
                    )
                )
            }
        }.onSuccess { userService.setUser(it.user.nickname) }
    }

    suspend fun register(email: String, password: String, name: String): Result<AuthResult> {
        return runCatching {
            api.register(email, password, name).let { response ->
                tokenManager.saveTokens(response.accessToken, response.refreshToken)
                AuthResult(
                    user = User(
                        id = response.user.id,
                        email = response.user.email,
                        name = response.user.name,
                        nickname = response.user.nickname
                    )
                )
            }
        }.onSuccess { userService.setUser(it.user.nickname) }
    }

    suspend fun logout(): Result<Unit> {
        return runCatching {
            val refreshToken = tokenManager.refreshToken.first()
                ?: throw IllegalStateException("refreshToken не найден")
            val accessToken = tokenManager.accessToken.first()
                ?: throw IllegalStateException("accessToken не найден")
            api.logout(accessToken = accessToken, refreshToken = refreshToken)
        }.onSuccess {
            tokenManager.clearTokens()
            userService.clearUser()
        }
    }

} 