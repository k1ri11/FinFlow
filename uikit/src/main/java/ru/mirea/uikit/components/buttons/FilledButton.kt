package ru.mirea.uikit.components.buttons

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.mirea.uikit.R
import ru.mirea.uikit.theme.FinFlowTheme


@Composable
fun FilledButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    @DrawableRes iconId: Int? = null,
    enabled: Boolean = true,
) {
    FilledBaseButton(
        label = label,
        onClick = onClick,
        modifier = modifier,
        iconId = iconId,
        enabled = enabled,
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun FilledButtonsPreviewLight() {
    FinFlowTheme {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            FilledBaseButton(
                enabled = true,
                label = "Далее",
                onClick = {},
                iconId = R.drawable.ic_logout
            )

            FilledBaseButton(
                enabled = false,
                label = "Далее",
                onClick = {},
                iconId = R.drawable.ic_logout
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun FilledButtonsPreviewDark() {
    FinFlowTheme {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            FilledBaseButton(
                enabled = true,
                label = "Выйти",
                onClick = {},
                iconId = R.drawable.ic_logout
            )

            FilledBaseButton(
                enabled = false,
                label = "Выйти",
                onClick = {},
                iconId = R.drawable.ic_logout
            )
        }
    }
}