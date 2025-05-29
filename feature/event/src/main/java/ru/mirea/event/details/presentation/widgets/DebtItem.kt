package ru.mirea.event.details.presentation.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ru.mirea.event.details.domain.model.DetailsDebt
import ru.mirea.uikit.theme.FinFlowTheme

@Composable
fun DebtItem(
    debtItem: DetailsDebt,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .clip(FinFlowTheme.shapes.large)
            .background(FinFlowTheme.colorScheme.background.secondary)
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "От: ${debtItem.fromUserId}",
                style = FinFlowTheme.typography.titleSmall,
                color = FinFlowTheme.colorScheme.text.primary
            )
            Text(
                modifier = Modifier.weight(1f),
                text = "Кому: ${debtItem.toUserId}",
                style = FinFlowTheme.typography.titleSmall,
                color = FinFlowTheme.colorScheme.text.primary
            )
        }
        Text(
            text = debtItem.amount.toString(),
            style = FinFlowTheme.typography.titleLarge,
            color = FinFlowTheme.colorScheme.text.positive,
        )
    }
}