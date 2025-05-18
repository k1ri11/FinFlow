package ru.mirea.event.add_event.domain.models

import ru.mirea.feature.friends.domain.model.Friend

data class Members(
    val friends: List<Friend> = emptyList(),
    val dummiesNames: List<String> = emptyList(),
)