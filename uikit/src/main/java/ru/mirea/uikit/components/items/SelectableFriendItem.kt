package ru.mirea.uikit.components.items

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import ru.mirea.uikit.theme.FinFlowTheme

@Composable
fun SelectableFriendItem(
    icon: String,
    name: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onSelect: (Boolean) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(FinFlowTheme.shapes.large)
            .background(FinFlowTheme.colorScheme.background.secondary)
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AsyncImage(
            model = icon,
            contentDescription = null,
            modifier = Modifier.size(48.dp),
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = Modifier.weight(1f),
            text = name,
            style = FinFlowTheme.typography.titleSmall,
            color = FinFlowTheme.colorScheme.text.primary
        )
        RadioButton(
            selected = isSelected,
            onClick = { onSelect(!isSelected) }
        )

    }
}


@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun SelectableFriendsScreenPreviewLight() {
    FinFlowTheme {
        SelectableFriendItem(
            icon = "solet",
            name = "Katheryn Carson",
            isSelected = true,
            onSelect = {}
        )
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun SelectableFriendsScreenPreviewDark() {
    FinFlowTheme {
        SelectableFriendItem(
            icon = "solet",
            name = "Katheryn Carson",
            isSelected = true,
            onSelect = {}
        )
    }
}