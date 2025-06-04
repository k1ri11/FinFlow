package ru.mirea.event.details.domain.model

data class Debt(
    val id: Int,
    val fromUserId: Int,
    val toUserId: Int,
    val amount: Int,
    val transactionId: Int,
)

data class Share(
    val id: Int,
    val userId: Int,
    val value: Int,
    val transactionId: Int,
)

data class Transaction(
    val id: Int,
    val eventId: Int,
    val name: String,
    val type: String,
    val fromUser: Int,
    val amount: Int,
    val datetime: String,
    val debts: List<Debt> = emptyList(),
    val shares: List<Share> = emptyList(),
) 