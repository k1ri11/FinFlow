package ru.mirea.auth_api

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<AuthResult>
    suspend fun register(email: String, password: String, name: String): Result<AuthResult>
    suspend fun logout(): Result<Unit>
}