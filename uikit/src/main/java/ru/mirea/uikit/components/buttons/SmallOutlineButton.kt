package ru.mirea.uikit.components.buttons

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.mirea.uikit.R
import ru.mirea.uikit.theme.FinFlowTheme

@Composable
fun SmallOutlinedButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    @DrawableRes iconId: Int? = null,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(FinFlowTheme.shapes.extraSmall)
            .background(FinFlowTheme.colorScheme.background.primary)
            .border(
                width = 1.dp,
                color = FinFlowTheme.colorScheme.border.brand,
                FinFlowTheme.shapes.extraSmall
            )
            .clickable { onClick() }
            .padding(horizontal = 8.dp, vertical = 4.dp),
    ) {
        Text(
            text = label,
            style = FinFlowTheme.typography.bodyMedium,
            color = FinFlowTheme.colorScheme.text.brand,
        )
        iconId?.let {
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(iconId),
                contentDescription = null,
                tint = FinFlowTheme.colorScheme.icon.brand
            )
        }

    }
}

@Preview
@Composable
private fun SmallOutlinedButtonPreview() {
    FinFlowTheme {
        SmallOutlinedButton(
            label = "Добавить",
            iconId = R.drawable.ic_plus,
            onClick = {}
        )
    }
}