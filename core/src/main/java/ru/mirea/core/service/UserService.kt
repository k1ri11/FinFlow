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

    val nickname: Flow<String?> = context.userDataStore.data.map { preferences ->
        preferences[nicknameKey]
    }

    suspend fun getCurrentUserNick(): String? = nickname.firstOrNull()

    suspend fun setUser(nickname: String) {
        context.userDataStore.edit { preferences ->
            preferences[nicknameKey] = nickname
        }
    }

    suspend fun clearUser() {
        context.userDataStore.edit { preferences ->
            preferences.remove(nicknameKey)
        }
    }
} 