package ru.mirea.event.details.presentation.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import ru.mirea.event.details.domain.model.OptimizedDebt
import ru.mirea.uikit.R
import ru.mirea.uikit.theme.FinFlowTheme

@Composable
fun OptimizedDebtItem(
    optimizedDebtItem: OptimizedDebt,
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
            model = optimizedDebtItem.fromUserId,
            contentDescription = null,
            modifier = Modifier.size(48.dp),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.placeholder_user),
            error = painterResource(R.drawable.placeholder_user),
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "От: ${optimizedDebtItem.fromUserId}",
                style = FinFlowTheme.typography.titleSmall,
                color = FinFlowTheme.colorScheme.text.primary
            )
            Text(
                modifier = Modifier.weight(1f),
                text = "Кому: ${optimizedDebtItem.toUserId}",
                style = FinFlowTheme.typography.titleSmall,
                color = FinFlowTheme.colorScheme.text.primary
            )
        }
        Text(
            text = optimizedDebtItem.amount.toString(),
            style = FinFlowTheme.typography.titleLarge,
            color = FinFlowTheme.colorScheme.text.positive,
        )
    }
}