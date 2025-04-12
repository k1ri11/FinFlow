package ru.mirea.uikit.components.buttons

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.mirea.uikit.R
import ru.mirea.uikit.theme.FinFlowTheme

@Composable
fun OutlinedButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    @DrawableRes iconId: Int? = null,
) {
    OutlinedBaseButton(
        label = label,
        onClick = onClick,
        modifier = modifier,
        iconId = iconId,
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun OutlinedButtonPreviewLight() {
    FinFlowTheme {
        OutlinedButton(
            label = "Войти",
            onClick = {},
            iconId = R.drawable.ic_logout,
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun OutlinedButtonPreviewDark() {
    FinFlowTheme {
        OutlinedButton(
            label = "Войти",
            onClick = {},
            iconId = R.drawable.ic_logout,
        )
    }
}