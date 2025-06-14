package ru.mirea.event.details.presentation.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ru.mirea.event.details.domain.model.Transaction
import ru.mirea.uikit.theme.FinFlowTheme

@Composable
fun TransactionItem(
    transaction: Transaction,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = transaction.name,
            maxLines = 3,
            style = FinFlowTheme.typography.bodyMedium,
            overflow = TextOverflow.Ellipsis,
            color = FinFlowTheme.colorScheme.text.primary,
        )
        Column(
            modifier = Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = transaction.datetime,
                style = FinFlowTheme.typography.bodySmall,
                textAlign = TextAlign.End,
                color = FinFlowTheme.colorScheme.text.secondary,
            )
            Text(
                text = "${transaction.amount}",
                style = FinFlowTheme.typography.bodyMedium,
                textAlign = TextAlign.End,
                color = FinFlowTheme.colorScheme.status.success,
            )


        }

    }
}