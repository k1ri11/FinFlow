package ru.mirea.expense.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransactionRequestDto(
    @SerialName("type") val type: String,
    @SerialName("from_user") val fromUser: Int,
    @SerialName("amount") val amount: Double,
    @SerialName("name") val name: String,
    @SerialName("portion") val portion: Map<Int, Int>,
    @SerialName("users") val users: List<Int>,
)