package ru.mirea.event.details.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OptimizedDebtDto(
    @SerialName("from_user_id") val fromUserId: Int,
    @SerialName("to_user_id") val toUserId: Int,
    @SerialName("amount") val amount: Int,
    @SerialName("event_id") val eventId: Int,
)