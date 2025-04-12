package ru.mirea.uikit.components.icon

import androidx.annotation.DrawableRes
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import ru.mirea.uikit.R
import ru.mirea.uikit.theme.FinFlowTheme

@Composable
fun FFIconButton(
    onClick: () -> Unit,
    @DrawableRes resId: Int,
    modifier: Modifier = Modifier,
    containerModifier: Modifier = Modifier,
    tint: Color = FinFlowTheme.colorScheme.icon.primary,
) {
    CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides Dp.Unspecified) {
        IconButton(
            modifier = containerModifier,
            onClick = onClick
        ) {
            Icon(
                modifier = modifier,
                painter = painterResource(id = resId),
                contentDescription = null,
                tint = tint
            )
        }
    }

}

@Preview
@Composable
private fun FFIconButtonPreview() {
    FinFlowTheme {
        FFIconButton(
            onClick = {},
            resId = R.drawable.ic_eye,
        )
    }

}