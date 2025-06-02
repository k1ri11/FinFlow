package ru.mirea.event.add_event.domain.models

import ru.mirea.friends_api.Friend

data class Members(
    val friends: List<Friend> = emptyList(),
    val dummiesNames: List<String> = emptyList(),
)