package ru.mirea.uikit.components.money_bar

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.mirea.uikit.R
import ru.mirea.uikit.theme.FinFlowTheme

@Composable
fun MoneyProgressBar(
    oweMoney: Int,
    alreadyOwed: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clip(FinFlowTheme.shapes.large)
            .background(FinFlowTheme.colorScheme.background.secondary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row {
            Text(
                text = "Всего должен",
                style = FinFlowTheme.typography.bodyMedium,
                color = FinFlowTheme.colorScheme.text.secondary
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "уже отдал",
                style = FinFlowTheme.typography.bodyMedium,
                color = FinFlowTheme.colorScheme.text.secondary
            )
        }
        Row {
            Text(
                text = stringResource(R.string.positive_balance_value, oweMoney),
                style = FinFlowTheme.typography.headlineMedium,
                color = FinFlowTheme.colorScheme.text.positive
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(R.string.negative_balance_value, alreadyOwed),
                style = FinFlowTheme.typography.headlineMedium,
                color = FinFlowTheme.colorScheme.text.negative
            )
        }
        LinearProgressIndicator(
            progress = { alreadyOwed.toFloat() / oweMoney.toFloat() },
            modifier = Modifier
                .clip(FinFlowTheme.shapes.large)
                .background(FinFlowTheme.colorScheme.background.primary)
                .fillMaxWidth()
                .height(15.dp),
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
        MoneyProgressBar(

            modifier = Modifier.fillMaxWidth(), oweMoney = 600, alreadyOwed = 300
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun MoneyProgressBarPreviewDark() {
    FinFlowTheme {
        MoneyProgressBar(
            modifier = Modifier.fillMaxWidth(), oweMoney = 600, alreadyOwed = 300

        )
    }
}