package ru.mirea.event.details.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DebtDto(
    val id: Int,
    @SerialName("from_user_id") val fromUserId: Int,
    @SerialName("to_user_id") val toUserId: Int,
    val amount: Int,
    @SerialName("transaction_id") val transactionId: Int,
)

@Serializable
data class ShareDto(
    val id: Int,
    @SerialName("user_id") val userId: Int,
    val value: Int,
    @SerialName("transaction_id") val transactionId: Int,
)

@Serializable
data class TransactionDto(
    val id: Int,
    @SerialName("event_id") val eventId: Int,
    val name: String,
    @SerialName("transaction_category_id") val transactionCategoryId: Int,
    val type: String,
    @SerialName("from_user") val fromUser: Int,
    val amount: Int,
    val datetime: String,
    val debts: List<DebtDto>? = null,
    val shares: List<ShareDto>? = null,
)

@Serializable
data class TransactionsResponseDto(
    val transactions: List<TransactionDto>,
) 