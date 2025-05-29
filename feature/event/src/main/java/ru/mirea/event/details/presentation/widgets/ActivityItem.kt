package ru.mirea.event.details.presentation.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import ru.mirea.event.details.domain.model.EventActivity
import ru.mirea.uikit.R
import ru.mirea.uikit.theme.FinFlowTheme

@Composable
fun ActivityItem(activity: EventActivity) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            modifier = Modifier
                .size(48.dp)
                .clip(FinFlowTheme.shapes.large),
            model = activity.iconId,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.placeholder),
            error = painterResource(R.drawable.placeholder)
        )

        Text(
            modifier = Modifier.weight(1f),
            text = activity.description,
            maxLines = 3,
            style = FinFlowTheme.typography.bodyMedium,
            overflow = TextOverflow.Ellipsis,
            color = FinFlowTheme.colorScheme.text.primary,
        )
        Text(
            text = activity.datetime,
            style = FinFlowTheme.typography.bodyMedium,
            color = FinFlowTheme.colorScheme.text.primary,
        )

    }
}