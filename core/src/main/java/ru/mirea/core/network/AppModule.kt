package ru.mirea.core.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import ru.mirea.core.util.AppDispatchers
import ru.mirea.core.util.Const.BASE_URL
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    fun provideClient(): HttpClient {
        return HttpClient(OkHttp) {
            defaultRequest {
                contentType(ContentType.Application.Json)
                url(BASE_URL)
            }

            expectSuccess = true

            install(Logging) {
                level = LogLevel.ALL
            }
            install(HttpTimeout) {
                connectTimeoutMillis = 30_000
                requestTimeoutMillis = 30_000
            }
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                        isLenient = true
                    }
                )
            }
        }
    }


    @Singleton
    @Provides
    fun provideDispatcher() = AppDispatchers()

}

