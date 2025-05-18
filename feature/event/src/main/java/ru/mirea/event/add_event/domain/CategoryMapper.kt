package ru.mirea.event.add_event.domain

import ru.mirea.event.add_event.data.CategoryDto
import ru.mirea.event.add_event.data.CategoryIconDto
import ru.mirea.event.add_event.domain.models.Category
import ru.mirea.event.add_event.domain.models.CategoryIcon

fun CategoryDto.toDomain(): Category = Category(
    id = id,
    name = name,
    icon = icon.toDomain()
)

fun CategoryIconDto.toDomain(): CategoryIcon = CategoryIcon(
    id = id,
    name = name,
    externalUuid = externalUuid
) 