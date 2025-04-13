package ru.mirea.profile.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.mirea.core.util.AppDispatchers
import ru.mirea.core.util.BaseViewModel
import ru.mirea.profile.domain.ProfileRepository
import ru.mirea.profile.domain.model.Profile
import ru.mirea.profile.presentation.ProfileEvent.BirthDateChanged
import ru.mirea.profile.presentation.ProfileEvent.EmailChanged
import ru.mirea.profile.presentation.ProfileEvent.NameChanged
import ru.mirea.profile.presentation.ProfileEvent.NicknameChanged
import ru.mirea.profile.presentation.ProfileEvent.PhoneChanged
import ru.mirea.profile.presentation.ProfileEvent.Submit
import ru.mirea.profile.presentation.ProfileEvent.ToggleEditMode
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository,
    private val dispatchers: AppDispatchers,
) : BaseViewModel<ProfileState, ProfileEvent, ProfileEffect>(ProfileState()) {

    private var originalProfile: Profile? = null
    private val changedFields = mutableSetOf<String>()

    init {
        loadProfile()
    }

    override fun event(event: ProfileEvent) {
        when (event) {
            is NameChanged -> updateName(event.name)
            is NicknameChanged -> updateNickname(event.nickname)
            is PhoneChanged -> updatePhone(event.phone)
            is EmailChanged -> updateEmail(event.email)
            is BirthDateChanged -> updateBirthDate(event.birthDate)
            ToggleEditMode -> toggleEditMode()
            Submit -> handleSubmit()
        }
    }

    private fun loadProfile() {
        viewModelScope.launch(dispatchers.io) {
            updateState { it.copy(isLoading = true) }
            repository.getProfile("kirill") // todo сделать прокидывание nickName
                .onSuccess { profile ->
                    originalProfile = profile
                    updateState {
                        it.copy(
                            isLoading = false,
                            profile = profile
                        )
                    }
                }
                .onFailure { error ->
                    updateState { it.copy(isLoading = false) }
                    emitEffect(ProfileEffect.ShowError(error.message ?: "Неизвестная ошибка"))
                }
        }
    }

    private fun updateName(name: String) {
        val original = originalProfile?.name
        if (name != original) {
            changedFields.add("name")
        } else {
            changedFields.remove("name")
        }
        updateState { it.copy(profile = it.profile?.copy(name = name)) }
    }

    private fun updateNickname(nickname: String) {
        val original = originalProfile?.nickname
        if (nickname != original) {
            changedFields.add("nickname")
        } else {
            changedFields.remove("nickname")
        }
        updateState { it.copy(profile = it.profile?.copy(nickname = nickname)) }
    }

    private fun updatePhone(phone: String) {
        val original = originalProfile?.phone
        if (phone != original) {
            changedFields.add("phone")
        } else {
            changedFields.remove("phone")
        }
        updateState { it.copy(profile = it.profile?.copy(phone = phone)) }
    }

    private fun updateEmail(email: String) {
        val original = originalProfile?.email
        if (email != original) {
            changedFields.add("email")
        } else {
            changedFields.remove("email")
        }
        updateState { it.copy(profile = it.profile?.copy(email = email)) }
    }

    private fun updateBirthDate(birthDate: String) {
        val original = originalProfile?.birthDate
        if (birthDate != original) {
            changedFields.add("birthDate")
        } else {
            changedFields.remove("birthDate")
        }
        updateState { it.copy(profile = it.profile?.copy(birthDate = birthDate)) }
    }

    private fun toggleEditMode() {
        val currentState = state.value
        if (currentState.isEditing) {
            // Если выключаем режим редактирования, восстанавливаем оригинальные значения
            updateState {
                it.copy(
                    isEditing = false,
                    profile = originalProfile
                )
            }
            changedFields.clear()
        } else {
            updateState { it.copy(isEditing = true) }
        }
    }

    private fun handleSubmit() {
        val currentState = state.value
        if (currentState.isLoading || currentState.profile == null || changedFields.isEmpty()) return

        viewModelScope.launch(dispatchers.io) {
            updateState { it.copy(isLoading = true) }

            repository.updateProfile(currentState.profile, changedFields)
                .onSuccess { profile ->
                    originalProfile = profile
                    changedFields.clear()
                    updateState {
                        it.copy(
                            isLoading = false,
                            isEditing = false,
                            profile = profile
                        )
                    }
                    emitEffect(ProfileEffect.ProfileUpdated)
                }
                .onFailure { error ->
                    updateState { it.copy(isLoading = false) }
                    emitEffect(ProfileEffect.ShowError(error.message ?: "Неизвестная ошибка"))
                }
        }
    }
} 