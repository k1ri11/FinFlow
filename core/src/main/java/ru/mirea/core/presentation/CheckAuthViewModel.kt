package ru.mirea.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.mirea.core.network.TokenManager
import ru.mirea.core.util.AppDispatchers
import javax.inject.Inject

@HiltViewModel
class CheckAuthViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val dispatchers: AppDispatchers,
) : ViewModel() {

    private val _isAuthorized = MutableStateFlow<Boolean?>(null)
    val isAuthorized: StateFlow<Boolean?> = _isAuthorized.asStateFlow()

    init {
        observeToken()
    }

    private fun observeToken() {
        viewModelScope.launch(dispatchers.io) {
            tokenManager.accessToken.collect { _isAuthorized.value = !it.isNullOrEmpty() }
        }
    }
}