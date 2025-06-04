package ru.mirea.core.service

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(name = "user_data")

@Singleton
class UserService @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private val nicknameKey = stringPreferencesKey("nickname")
    private val userIdKey = stringPreferencesKey("userId")

    val nickname: Flow<String?> = context.userDataStore.data.map { preferences ->
        preferences[nicknameKey]
    }

    val userId: Flow<Int?> = context.userDataStore.data.map { preferences ->
        preferences[userIdKey]?.toIntOrNull()
    }

    suspend fun getCurrentUserNick(): String? = nickname.firstOrNull()

    suspend fun getCurrentUserId(): Int? = userId.firstOrNull()

    suspend fun setUser(nickname: String, userid: Int) {
        context.userDataStore.edit { preferences ->
            preferences[nicknameKey] = nickname
        }
        context.userDataStore.edit { preferences ->
            preferences[userIdKey] = userid.toString()
        }
    }

    suspend fun clearUser() {
        context.userDataStore.edit { preferences ->
            preferences.remove(nicknameKey)
        }
    }
} 