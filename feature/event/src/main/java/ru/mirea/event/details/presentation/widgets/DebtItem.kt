package ru.mirea.event.details.presentation.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import ru.mirea.event.details.domain.model.DetailsDebt
import ru.mirea.uikit.R
import ru.mirea.uikit.theme.FinFlowTheme

@Composable
fun DebtItem(
    debtItem: DetailsDebt,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .clip(FinFlowTheme.shapes.large)
            .background(FinFlowTheme.colorScheme.background.secondary)
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AsyncImage(
            model = debtItem.id,
            contentDescription = null,
            modifier = Modifier.size(48.dp),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.placeholder_user),
            error = painterResource(R.drawable.placeholder_user),
        )
        Text(
            modifier = Modifier.weight(1f),
            text = debtItem.requesterName,
            style = FinFlowTheme.typography.titleSmall,
            color = FinFlowTheme.colorScheme.text.primary
        )
        Text(
            text = if (debtItem.amount > 0) stringResource(
                R.string.positive_balance_value,
                debtItem.amount
            )
            else stringResource(R.string.negative_balance_value, debtItem.amount),
            style = FinFlowTheme.typography.bodyMedium,
            color = if (debtItem.amount > 0) FinFlowTheme.colorScheme.text.positive
            else FinFlowTheme.colorScheme.text.negative
        )
    }
}