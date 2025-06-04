package ru.mirea.expense.data.models

import ru.mirea.expense.domain.model.EventUser
import ru.mirea.expense.domain.model.UserProfile

fun EventUserResponseDto.toDomain(): EventUser = EventUser(
    id = id,
    name = name,
    isDummy = isDummy,
    profile = profile?.toDomain()
)

fun ProfileDto.toDomain(): UserProfile = UserProfile(
    userId = userId,
    nickname = nickname,
    name = name,
    photo = photo
) 