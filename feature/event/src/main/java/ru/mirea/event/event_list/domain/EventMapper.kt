package ru.mirea.event.event_list.domain

import ru.mirea.event.event_list.data.EventDto

fun EventDto.toDomain(): Event = Event(
    id = id,
    name = name,
    description = description,
    categoryId = categoryId
)