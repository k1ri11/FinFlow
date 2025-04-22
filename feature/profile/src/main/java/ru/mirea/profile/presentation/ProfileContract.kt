package ru.mirea.profile.presentation

import ru.mirea.profile.domain.model.Profile

data class ProfileState(
    val isLoading: Boolean = false,
    val profile: Profile? = null,
    val isEditing: Boolean = false,
)

sealed interface ProfileEvent {
    data class NameChanged(val name: String) : ProfileEvent
    data class NicknameChanged(val nickname: String) : ProfileEvent
    data class PhoneChanged(val phone: String) : ProfileEvent
    data class EmailChanged(val email: String) : ProfileEvent
    data class BirthDateChanged(val birthDate: String) : ProfileEvent
    data object ToggleEditMode : ProfileEvent
    data object Submit : ProfileEvent
    data object Logout : ProfileEvent
}

sealed interface ProfileEffect {
    data class ShowError(val message: String) : ProfileEffect
    data object ProfileUpdated : ProfileEffect
    data object LoggedOut : ProfileEffect
} 