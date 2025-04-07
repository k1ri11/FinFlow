package ru.mirea.auth.domain

import ru.mirea.auth.data.AuthApi
import ru.mirea.auth.domain.model.AuthResult
import ru.mirea.auth.domain.model.User
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: AuthApi
) {
    suspend fun login(email: String, password: String): Result<AuthResult> = runCatching {
        api.login(email, password).let { response ->
            AuthResult(
                user = User(
                    id = response.user.id,
                    email = response.user.email,
                    name = response.user.name
                )
            )
        }
    }

    suspend fun register(email: String, password: String, name: String): Result<AuthResult> =
        runCatching {
            api.register(email, password, name).let { response ->
                AuthResult(
                    user = User(
                        id = response.user.id,
                        email = response.user.email,
                        name = response.user.name
                    )
                )
            }
        }
} 