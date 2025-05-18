package ru.mirea.event.add_event.domain.models

/**
 * Domain-модель категории события
 */
data class Category(
    val id: Int,
    val name: String,
    val icon: CategoryIcon,
)

data class CategoryIcon(
    val id: Int,
    val name: String,
    val externalUuid: String,
) 