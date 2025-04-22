package ru.mirea.feature.friends.presentation.widgets

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import ru.mirea.feature.friends.domain.model.Friend
import ru.mirea.uikit.R
import ru.mirea.uikit.theme.FinFlowTheme

@Composable
fun FriendItem(
    friend: Friend,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(FinFlowTheme.shapes.large)
            .background(FinFlowTheme.colorScheme.background.secondary)
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AsyncImage(
            model = friend.icon,
            contentDescription = null,
            modifier = Modifier.size(48.dp),
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = Modifier.weight(1f),
            text = friend.name,
            style = FinFlowTheme.typography.titleSmall,
            color = FinFlowTheme.colorScheme.text.primary
        )
        Text(
            text = if (friend.isPositive) stringResource(
                R.string.positive_balance_value,
                friend.owe
            )
            else stringResource(R.string.negative_balance_value, friend.owe),
            style = FinFlowTheme.typography.bodyMedium,
            color = if (friend.isPositive) FinFlowTheme.colorScheme.text.positive
            else FinFlowTheme.colorScheme.text.negative
        )

    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun FriendsScreenPreviewLight() {
    FinFlowTheme {
        FriendItem(
            friend = Friend(
                icon = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQmVq-OmHL5H_5P8b1k306pFddOe3049-il2A&s",
                name = "Иван Калита",
                owe = 300,
                id = 1,
                isPositive = true,
                status = ""
            )
        )
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun FriendsScreenPreviewDark() {
    FinFlowTheme {
        FriendItem(
            friend = Friend(
                icon = "",
                name = "Иван Калита",
                owe = 300,
                isPositive = true,
                id = 1,
                status = ""
            )
        )
    }
}

