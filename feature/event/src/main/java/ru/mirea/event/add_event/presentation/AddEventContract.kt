package ru.mirea.event.add_event.presentation

import ru.mirea.event.add_event.domain.models.Members

data class AddEventState(
    val name: String = "",
    val description: String = "",
    val icon: String? = null,
    val categories: List<Category> = listOf(
        Category(id = 0, name = "Клубешник", iconId = ""),
        Category(id = 1, name = "Орешник", iconId = ""),
        Category(id = 2, name = "Бухарешник", iconId = ""),
    ),
    val categoryId: Int? = null,
    val members: Members = Members(),
) {

    data class Category(
        val id: Int,
        val name: String,
        val iconId: String,
    )

}

sealed interface AddEventEvent {
    data class NameChanged(val name: String) : AddEventEvent
    data class DescriptionChanged(val description: String) : AddEventEvent
    data class CategoryChanged(val categoryId: Int) : AddEventEvent
    data class UpdateMembers(val members: Members) : AddEventEvent
}

sealed interface AddEventEffect {
    data class ShowError(val message: String) : AddEventEffect
    data object Success : AddEventEffect
}