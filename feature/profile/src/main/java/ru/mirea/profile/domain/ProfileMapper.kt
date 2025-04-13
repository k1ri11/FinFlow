package ru.mirea.profile.domain

import ru.mirea.profile.data.model.ProfileDto
import ru.mirea.profile.data.model.ProfilePatchDto
import ru.mirea.profile.domain.model.Profile

fun ProfileDto.toDomain(): Profile = Profile(
    id = id,
    email = email,
    phone = phone,
    name = name,
    nickname = nickname,
    birthDate = birthDate,
    avatarUrl = avatarUrl
)

fun Profile.toPatchDto(changedFields: Set<String>): ProfilePatchDto = ProfilePatchDto(
    email = if (changedFields.contains("email")) email else null,
    phone = if (changedFields.contains("phone")) phone else null,
    name = if (changedFields.contains("name")) name else null,
    nickname = if (changedFields.contains("nickname")) nickname else null,
    birthDate = if (changedFields.contains("birthDate")) birthDate else null,
    avatarUrl = if (changedFields.contains("avatarUrl")) avatarUrl else null
)