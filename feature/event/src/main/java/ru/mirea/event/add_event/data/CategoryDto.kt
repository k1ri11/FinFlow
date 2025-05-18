package ru.mirea.event.add_event.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryListResponseDto(
    @SerialName("categories")
    val categories: List<CategoryDto>,
)

@Serializable
data class CategoryDto(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("icon")
    val icon: CategoryIconDto,
)

@Serializable
data class CategoryIconDto(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("external_uuid")
    val externalUuid: String,
) 