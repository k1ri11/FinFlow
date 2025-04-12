package ru.mirea.uikit.components.buttons

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.mirea.uikit.R
import ru.mirea.uikit.theme.FinFlowTheme
import ru.mirea.uikit.theme.LightColors

@Composable
fun FilledBaseButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    @DrawableRes iconId: Int? = null,
    enabled: Boolean = true,
    colors: ButtonColors = filledButtonColors(),
    iconTint: Color = if (enabled) colors.contentColor else colors.disabledContentColor,
    textStyle: TextStyle = FinFlowTheme.typography.bodyMedium.copy(
        fontWeight = FontWeight.Medium,
        color = Color.Unspecified
    ),
    contentPaddingValues: PaddingValues = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp),
        enabled = enabled,
        colors = colors,
        onClick = onClick,
        shape = FinFlowTheme.shapes.small,
        contentPadding = contentPaddingValues,
    ) {
        iconId?.let {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = null,
                tint = iconTint
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(
            text = label,
            style = textStyle,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun filledButtonColors(
    containerColor: Color = FinFlowTheme.colorScheme.background.brand,
    contentColor: Color = LightColors.White,
    disabledContainerColor: Color = FinFlowTheme.colorScheme.background.tertiary,
    disabledContentColor: Color = LightColors.LightGray,
): ButtonColors = ButtonColors(
    containerColor = containerColor,
    contentColor = contentColor,
    disabledContainerColor = disabledContainerColor,
    disabledContentColor = disabledContentColor
)

@Preview
@Composable
private fun BaseButtonPreview() {
    FinFlowTheme {
        FilledBaseButton(
            enabled = true,
            modifier = Modifier.fillMaxWidth(),
            label = "Выйти",
            onClick = {},
            iconId = R.drawable.ic_logout
        )
    }
}