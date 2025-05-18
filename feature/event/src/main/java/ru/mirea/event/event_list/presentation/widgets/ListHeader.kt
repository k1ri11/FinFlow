package ru.mirea.event.event_list.presentation.widgets

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.mirea.uikit.R
import ru.mirea.uikit.components.buttons.SmallOutlinedButton
import ru.mirea.uikit.theme.FinFlowTheme

@Composable
fun ListHeader(
    onAddEventClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = "События",
            style = FinFlowTheme.typography.bodyLarge,
            color = FinFlowTheme.colorScheme.text.primary
        )
        SmallOutlinedButton(
            label = "Добавить",
            iconId = R.drawable.ic_plus,
            onClick = onAddEventClick
        )
    }
}

@Preview
@Composable
private fun ListHeaderPreview() {
    FinFlowTheme {
        ListHeader(onAddEventClick = {})
    }
}