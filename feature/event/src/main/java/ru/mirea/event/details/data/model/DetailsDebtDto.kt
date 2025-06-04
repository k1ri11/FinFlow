package ru.mirea.event.details.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DetailsDebtDto(
    @SerialName("id") val id: Int,
    @SerialName("from_user_id") val fromUserId: Int,
    @SerialName("to_user_id") val toUserId: Int,
    @SerialName("amount") val amount: Int,
    @SerialName("transaction_id") val transactionId: Int,
    @SerialName("requestor") val requestor: Requestor,
) {
    @Serializable
    data class Requestor(
        @SerialName("name") val name: String,
    )
}