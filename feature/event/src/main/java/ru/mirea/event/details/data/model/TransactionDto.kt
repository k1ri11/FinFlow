package ru.mirea.event.details.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DebtDto(
    @SerialName("id") val id: Int,
    @SerialName("from_user_id") val fromUserId: Int,
    @SerialName("to_user_id") val toUserId: Int,
    @SerialName("amount") val amount: Float,
    @SerialName("transaction_id") val transactionId: Int,
)

@Serializable
data class ShareDto(
    @SerialName("id") val id: Int,
    @SerialName("user_id") val userId: Int,
    @SerialName("value") val value: Float,
    @SerialName("transaction_id") val transactionId: Int,
)

@Serializable
data class TransactionDto(
    @SerialName("id") val id: Int,
    @SerialName("event_id") val eventId: Int,
    @SerialName("name") val name: String,
    @SerialName("type") val type: String,
    @SerialName("from_user") val fromUser: Int,
    @SerialName("amount") val amount: Int,
    @SerialName("datetime") val datetime: String,
    @SerialName("debts") val debts: List<DebtDto>? = null,
    @SerialName("shares") val shares: List<ShareDto>? = null,
)

@Serializable
data class TransactionsResponseDto(
    @SerialName("transactions") val transactions: List<TransactionDto>,
) 