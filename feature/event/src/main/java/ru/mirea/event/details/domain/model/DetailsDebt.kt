package ru.mirea.event.details.domain.model

data class DetailsDebt(
    val id: Int,
    val fromUserId: Int,
    val toUserId: Int,
    val amount: Int,
    val transactionId: Int,
    val requesterName: String,
)