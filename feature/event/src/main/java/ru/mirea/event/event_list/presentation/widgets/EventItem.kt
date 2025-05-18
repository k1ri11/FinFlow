package ru.mirea.event.event_list.presentation.widgets

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import ru.mirea.event.event_list.domain.Event
import ru.mirea.uikit.R
import ru.mirea.uikit.theme.FinFlowTheme

@Composable
fun EventItem(
    event: Event,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(FinFlowTheme.shapes.large)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .size(80.dp)
                .clip(FinFlowTheme.shapes.small),
            model = event.photoId,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                modifier = Modifier,
                text = event.name,
                style = FinFlowTheme.typography.titleSmall,
                color = FinFlowTheme.colorScheme.text.primary
            )

            Text(
                modifier = Modifier,
                text = event.category,
                style = FinFlowTheme.typography.bodyMedium,
                color = FinFlowTheme.colorScheme.text.secondary
            )

        }
        event.balance?.let {
            Text(
                text = if (event.isPositive) stringResource(
                    R.string.positive_balance_value,
                    event.balance
                )
                else stringResource(R.string.negative_balance_value, event.balance),
                style = FinFlowTheme.typography.bodyMedium,
                color = if (event.isPositive) FinFlowTheme.colorScheme.text.positive
                else FinFlowTheme.colorScheme.text.negative
            )
        }

    }
}


@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun FriendsScreenPreviewLight() {
    FinFlowTheme {
        EventItem(
            Event(
                id = 1,
                name = "Шашлындос",
                category = "Сходка",
                photoId = "",
                balance = 200,
                isPositive = true
            ),
            onClick = {}
        )
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun FriendsScreenPreviewDark() {
    FinFlowTheme {
        EventItem(
            Event(
                id = 1,
                name = "Шашлындос",
                category = "Сходка",
                photoId = "",
                balance = 200,
                isPositive = true
            ),
            onClick = {}
        )
    }
}