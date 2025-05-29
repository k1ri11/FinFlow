package ru.mirea.event.details.presentation.widgets

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import ru.mirea.uikit.R
import ru.mirea.uikit.components.buttons.SmallActionButton
import ru.mirea.uikit.components.money_bar.GroupTabs
import ru.mirea.uikit.theme.FinFlowTheme

@Composable
fun DetailsTopCard(
    cardData: CardData,
    onTabSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(FinFlowTheme.shapes.large)
            .background(FinFlowTheme.colorScheme.background.secondary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        IconWithMoneyBar(cardData)

        MembersRow(cardData)

        ButtonsRow(onTabSelect)
    }
}

@Composable
private fun IconWithMoneyBar(cardData: CardData) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .size(72.dp)
                .clip(FinFlowTheme.shapes.large),
            model = cardData.iconUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.placeholder),
            error = painterResource(R.drawable.placeholder)
        )
        EventDetailsMoneyBar(cardData.oweMoney, cardData.alreadyOwed)
    }
}

@Composable
private fun MembersRow(cardData: CardData) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Участники события",
            style = FinFlowTheme.typography.bodyMedium,
            color = FinFlowTheme.colorScheme.text.primary,
        )
        Box(modifier = Modifier.fillMaxWidth()) {
            cardData.membersIconUrls.forEachIndexed { index, iconUrl ->
                AsyncImage(
                    modifier = Modifier
                        .padding(start = (index * 20).dp)
                        .size(24.dp)
                        .clip(FinFlowTheme.shapes.large),
                    model = iconUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(R.drawable.placeholder),
                    error = painterResource(R.drawable.placeholder)
                )
            }
        }

    }
}

@Composable
private fun ButtonsRow(onTabSelect: (Int) -> Unit) {
    Row(
        modifier = Modifier.padding(top = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        GroupTabs.entries.toList().forEachIndexed { index, tab ->
            SmallActionButton(
                modifier = Modifier.weight(1f),
                text = tab.title,
                onClick = { onTabSelect(index) },
                selected = true
            )
        }
    }
}

@Composable
private fun EventDetailsMoneyBar(
    oweMoney: Int,
    alreadyOwed: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(start = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row {
            Text(
                text = "Всего должен",
                style = FinFlowTheme.typography.bodySmall,
                color = FinFlowTheme.colorScheme.text.secondary
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "уже отдал",
                style = FinFlowTheme.typography.bodySmall,
                color = FinFlowTheme.colorScheme.text.secondary
            )
        }
        Row {
            Text(
                text = stringResource(R.string.positive_balance_value, oweMoney),
                style = FinFlowTheme.typography.bodyMedium,
                color = FinFlowTheme.colorScheme.text.positive
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(R.string.negative_balance_value, alreadyOwed),
                style = FinFlowTheme.typography.bodyMedium,
                color = FinFlowTheme.colorScheme.text.negative
            )
        }
        LinearProgressIndicator(
            progress = { alreadyOwed.toFloat() / oweMoney.toFloat() },
            modifier = Modifier
                .clip(FinFlowTheme.shapes.large)
                .background(FinFlowTheme.colorScheme.background.primary)
                .fillMaxWidth()
                .height(12.dp),
            gapSize = 0.dp,
            trackColor = FinFlowTheme.colorScheme.background.primary,
            drawStopIndicator = {},
            strokeCap = StrokeCap.Round
        )
    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun MoneyProgressBarPreviewLight() {
    FinFlowTheme {
        DetailsTopCard(
            CardData(
                oweMoney = 8610,
                alreadyOwed = 6615,
                iconUrl = "",
                membersIconUrls = listOf("", "", "")
            ),
            onTabSelect = {}
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun MoneyProgressBarPreviewDark() {
    MoneyProgressBarPreviewLight()
}

data class CardData(
    val oweMoney: Int,
    val alreadyOwed: Int,
    val iconUrl: String,
    val membersIconUrls: List<String>,
)