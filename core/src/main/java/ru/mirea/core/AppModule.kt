package ru.mirea.core

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json
import ru.mirea.core.network.TokenManager
import ru.mirea.core.network.model.RefreshTokenResponse
import ru.mirea.core.util.AppDispatchers
import ru.mirea.core.util.Const.BASE_URL
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideDispatcher() = AppDispatchers()

    @Singleton
    @Provides
    fun provideClient(tokenManager: TokenManager): HttpClient {
        return HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }

            defaultRequest {
                contentType(ContentType.Application.Json)
                url(BASE_URL)
            }

            install(HttpTimeout) {
                connectTimeoutMillis = 30_000
                requestTimeoutMillis = 30_000
            }

            install(Logging) {
                level = LogLevel.ALL
            }

            install(Auth) {
                bearer {
                    loadTokens {
                        val accessToken = tokenManager.accessToken.first()
                        val refreshToken = tokenManager.refreshToken.first()
                        if (accessToken != null && refreshToken != null) {
                            BearerTokens(accessToken, refreshToken)
                        } else null
                    }

                    refreshTokens {
                        // Если нет старых токенов, значит пользователь не авторизован
                        val oldTokens = oldTokens ?: return@refreshTokens null

                        try {
                            val response = client.post("auth/refresh") {
                                contentType(ContentType.Application.Json)
                                setBody(mapOf("refresh_token" to oldTokens.refreshToken))
                            }

                            val tokens = response.body<RefreshTokenResponse>()
                            tokenManager.saveTokens(tokens.accessToken, tokens.refreshToken)

                            BearerTokens(tokens.accessToken, tokens.refreshToken)

                        } catch (e: Exception) {
                            tokenManager.clearTokens()
                            null
                        }
                    }
                }
            }
        }
    }

}

