package ru.mirea.event.details.domain

import ru.mirea.event.details.data.api.DetailsDebtDto
import ru.mirea.event.details.domain.model.DetailsDebt

fun DetailsDebtDto.toDomain(): DetailsDebt = DetailsDebt(
    id = id,
    fromUserId = fromUserId,
    toUserId = toUserId,
    amount = amount,
    transactionId = transactionId
) 