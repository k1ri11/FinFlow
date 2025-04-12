package ru.mirea.uikit.components.buttons

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.mirea.uikit.R
import ru.mirea.uikit.theme.FinFlowTheme


@Composable
fun OutlinedBaseButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    @DrawableRes iconId: Int? = null,
    colors: OutlinedButtonColors = outlinedButtonColors(),
    iconTint: Color = colors.materialButtonColors.contentColor,
    textStyle: TextStyle = FinFlowTheme.typography.bodyMedium.copy(
        fontWeight = FontWeight.Medium,
        color = Color.Unspecified
    ),
    contentPaddingValues: PaddingValues = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
) {
    OutlinedButton(
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp),
        onClick = onClick,
        colors = colors.materialButtonColors,
        shape = FinFlowTheme.shapes.small,
        border = BorderStroke(
            width = 2.dp,
            color = colors.outlineBorderColor,
        ),
        contentPadding = contentPaddingValues
    ) {
        iconId?.let {
            Icon(
                painter = painterResource(iconId),
                contentDescription = null,
                tint = iconTint
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(
            text = label,
            style = textStyle,
        )
    }
}

@Composable
fun outlinedButtonColors(
    contentColor: Color = FinFlowTheme.colorScheme.border.brand,
    outlineColor: Color = FinFlowTheme.colorScheme.border.brand,
): OutlinedButtonColors =
    OutlinedButtonColors(
        materialButtonColors = ButtonColors(
            containerColor = FinFlowTheme.colorScheme.background.primary,
            contentColor = contentColor,
            disabledContainerColor = FinFlowTheme.colorScheme.background.tertiary,
            disabledContentColor = FinFlowTheme.colorScheme.text.secondary,
        ),
        outlineBorderColor = outlineColor,
    )

@Immutable
data class OutlinedButtonColors(
    val materialButtonColors: ButtonColors,
    val outlineBorderColor: Color,
)

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun OutlinedBaseButtonPreview() {
    FinFlowTheme {
        OutlinedBaseButton(
            label = "Войти",
            onClick = {},
            iconId = R.drawable.ic_logout
        )
    }
}
