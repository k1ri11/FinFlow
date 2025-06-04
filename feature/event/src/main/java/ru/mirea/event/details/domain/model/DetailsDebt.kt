package ru.mirea.event.details.domain.model

data class DetailsDebt(
    val id: Int,
    val fromUserId: Int,
    val toUserId: Int,
    val amount: Float,
    val transactionId: Int? = null,
    val requesterName: String,
    val isPositive: Boolean,
)