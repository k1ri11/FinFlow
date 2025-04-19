package ru.mirea.uikit.components.buttons

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.mirea.uikit.theme.FinFlowTheme
import ru.mirea.uikit.utils.conditional

@Composable
fun SmallActionButton(
    selected: Boolean,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val actionButtonColor = FinFlowTheme.colorScheme.border.action
    Box(
        modifier = modifier
            .clip(FinFlowTheme.shapes.large)
            .conditional(selected) {
                background(actionButtonColor)
            }
            .border(1.dp, color = actionButtonColor, shape = FinFlowTheme.shapes.large)
            .clickable { onClick() },

        ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(8.dp),
            text = text,
            style = FinFlowTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium),
            color = if (selected) FinFlowTheme.colorScheme.text.primary else actionButtonColor
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun SmallActionButtonPreview() {
    FinFlowTheme {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            SmallActionButton(true, "Подробнее", {})
            SmallActionButton(false, "Подробнее", {})
        }

    }
}