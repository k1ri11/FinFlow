package ru.mirea.event.details.domain.model

data class OptimizedDebt(
    val fromUserId: Int,
    val toUserId: Int,
    val amount: Int,
    val eventId: Int,
) 