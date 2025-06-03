package ru.mirea.event.event_list.presentation.widgets

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.style.TextOverflow
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
                .clip(FinFlowTheme.shapes.large),
            model = event.iconId,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.placeholder_money),
            error = painterResource(R.drawable.placeholder_money)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
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
                text = event.description,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = FinFlowTheme.typography.bodyMedium,
                color = FinFlowTheme.colorScheme.text.secondary
            )

        }
        event.balance?.let {
            Text(
                text = if (event.isPositive == true) stringResource(
                    R.string.positive_balance_value,
                    event.balance
                )
                else stringResource(R.string.negative_balance_value, event.balance),
                style = FinFlowTheme.typography.bodyMedium,
                color = if (event.isPositive == true) FinFlowTheme.colorScheme.text.positive
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
                description = "Сходка",
                balance = 200,
                categoryId = 1,
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
                description = "Сходка",
                balance = 200,
                categoryId = 1,
                isPositive = true
            ),
            onClick = {}
        )
    }
}