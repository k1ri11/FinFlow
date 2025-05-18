package ru.mirea.event.add_event.presentation

import ru.mirea.event.add_event.domain.models.Category
import ru.mirea.event.add_event.domain.models.Members

data class AddEventState(
    val name: String = "",
    val description: String = "",
    val icon: String? = null,
    val categories: List<Category> = listOf(),
    val categoryId: Int? = null,
    val members: Members = Members(),
)

sealed interface AddEventEvent {
    data class NameChanged(val name: String) : AddEventEvent
    data class DescriptionChanged(val description: String) : AddEventEvent
    data class CategoryChanged(val categoryId: Int) : AddEventEvent
    data class UpdateMembers(val members: Members) : AddEventEvent
    data object CreateEvent : AddEventEvent
}

sealed interface AddEventEffect {
    data class ShowError(val message: String) : AddEventEffect
    data object Success : AddEventEffect
}