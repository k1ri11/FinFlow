package ru.mirea.uikit.components.top_bar

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.mirea.uikit.R
import ru.mirea.uikit.components.icon.FFIconButton
import ru.mirea.uikit.theme.FinFlowTheme

@Composable
fun CommonTopBar(
    title: String,
    modifier: Modifier = Modifier,
    @DrawableRes leftIconId: Int? = null,
    onLeftIconClick: () -> Unit = {},
    @DrawableRes rightIconId: Int? = null,
    onRightIconClick: () -> Unit = {},
) {
    Row(
        modifier = modifier.background(color = FinFlowTheme.colorScheme.background.primary),
        verticalAlignment = Alignment.CenterVertically
    ) {
        leftIconId?.let { iconId ->
            FFIconButton(
                onClick = onLeftIconClick,
                resId = iconId
            )
        } ?: Box(Modifier.size(40.dp))
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            text = title,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            color = FinFlowTheme.colorScheme.text.primary
        )

        rightIconId?.let { iconId ->
            FFIconButton(
                onClick = onRightIconClick,
                resId = iconId
            )
        } ?: Box(Modifier.size(40.dp))
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun CommonTopBarPreviewLight() {
    FinFlowTheme {
        CommonTopBar(
            title = "Профиль",
            leftIconId = R.drawable.ic_arrow_back,
            onLeftIconClick = {},
            rightIconId = R.drawable.ic_edit,
            onRightIconClick = {}
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun CommonTopBarPreviewDark() {
    FinFlowTheme {
        CommonTopBar(
            title = "Профиль",
            leftIconId = R.drawable.ic_arrow_back,
            onLeftIconClick = {},
            rightIconId = R.drawable.ic_edit,
            onRightIconClick = {}
        )
    }
}