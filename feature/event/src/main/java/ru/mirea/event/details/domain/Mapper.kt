package ru.mirea.event.details.domain

import ru.mirea.event.details.data.model.DebtDto
import ru.mirea.event.details.data.model.DetailsDebtDto
import ru.mirea.event.details.data.model.OptimizedDebtDto
import ru.mirea.event.details.data.model.ShareDto
import ru.mirea.event.details.data.model.TransactionDto
import ru.mirea.event.details.domain.model.Debt
import ru.mirea.event.details.domain.model.DetailsDebt
import ru.mirea.event.details.domain.model.OptimizedDebt
import ru.mirea.event.details.domain.model.Share
import ru.mirea.event.details.domain.model.Transaction
import java.time.format.DateTimeFormatter
import java.util.Locale

fun DetailsDebtDto.toDomain(): DetailsDebt = DetailsDebt(
    id = id,
    fromUserId = fromUserId,
    toUserId = toUserId,
    amount = amount,
    transactionId = transactionId,
    requesterName = requestor.name,
    isPositive = amount >= 0
)

private fun formatDate(dateTime: String): String {
    return try {
        val parsed = java.time.OffsetDateTime.parse(dateTime)
        val formatter = DateTimeFormatter.ofPattern("d MMMM", Locale("ru"))
        parsed.format(formatter)
    } catch (e: Exception) {
        dateTime.take(10)
    }
}

fun DebtDto.toDomain(): Debt = Debt(
    id = id,
    fromUserId = fromUserId,
    toUserId = toUserId,
    amount = amount,
    transactionId = transactionId
)

fun ShareDto.toDomain(): Share = Share(
    id = id,
    userId = userId,
    value = value,
    transactionId = transactionId
)

fun TransactionDto.toDomain(): Transaction = Transaction(
    id = id,
    eventId = eventId,
    name = name,
    type = type,
    fromUser = fromUser,
    amount = amount,
    datetime = formatDate(datetime),
    debts = debts?.map { it.toDomain() } ?: emptyList(),
    shares = shares?.map { it.toDomain() } ?: emptyList()
)

fun OptimizedDebtDto.toDomain() = OptimizedDebt(
    fromUserId = fromUserId,
    toUserId = toUserId,
    amount = amount,
    eventId = eventId
)