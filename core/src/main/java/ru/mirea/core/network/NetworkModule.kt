package ru.mirea.core.network

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
import io.ktor.http.ContentType.Application.Json
import io.ktor.http.contentType
import io.ktor.http.encodedPath
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json
import ru.mirea.core.network.model.RefreshTokenResponse
import ru.mirea.core.service.UserService
import ru.mirea.core.util.Const.AUTH_BASE_URL
import ru.mirea.core.util.Const.ID_BASE_URL
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IdClient


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    @AuthClient
    fun provideAuthClient(tokenManager: TokenManager, userService: UserService): HttpClient {
        return HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }

            defaultRequest {
                contentType(Json)
                url(AUTH_BASE_URL)
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
                        try {
                            // Если нет старых токенов, значит пользователь не авторизован
                            val oldTokens = oldTokens ?: run {
                                tokenManager.clearTokens()
                                return@refreshTokens null
                            }

                            val response = client.post("auth/refresh") {
                                contentType(Json)
                                markAsRefreshTokenRequest()
                                setBody(mapOf("refresh_token" to oldTokens.refreshToken))
                            }

                            val tokens = response.body<RefreshTokenResponse>()
                            tokenManager.saveTokens(tokens.accessToken, tokens.refreshToken)

                            BearerTokens(tokens.accessToken, tokens.refreshToken)

                        } catch (e: Exception) {
                            // Всегда очищаем токены при любой ошибке обновления
                            tokenManager.clearTokens()
                            userService.clearUser()
                            null
                        }
                    }

                    sendWithoutRequest { request ->
                        request.url.host == AUTH_BASE_URL && request.url.encodedPath.contains("auth/refresh")
                    }
                }
            }
        }
    }

    @Singleton
    @Provides
    @IdClient
    fun provideIdClient(tokenManager: TokenManager): HttpClient {
        return HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }

            defaultRequest {
                contentType(Json)
                url(ID_BASE_URL)
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
                        try {
                            // Если нет старых токенов, значит пользователь не авторизован
                            val oldTokens = oldTokens ?: run {
                                tokenManager.clearTokens()
                                return@refreshTokens null
                            }

                            val tokens = client.post("${AUTH_BASE_URL}auth/refresh") {
                                contentType(Json)
                                markAsRefreshTokenRequest()
                                setBody(mapOf("refresh_token" to oldTokens.refreshToken))
                            }.body<RefreshTokenResponse>()

                            tokenManager.saveTokens(tokens.accessToken, tokens.refreshToken)

                            BearerTokens(tokens.accessToken, tokens.refreshToken)

                        } catch (e: Exception) {
                            // Всегда очищаем токены при любой ошибке обновления
                            tokenManager.clearTokens()
                            null
                        }
                    }

                }
            }
        }
    }


}