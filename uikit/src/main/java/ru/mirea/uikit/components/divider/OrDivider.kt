package ru.mirea.uikit.components.divider

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.mirea.uikit.R
import ru.mirea.uikit.theme.FinFlowTheme

@Composable
fun OrDivider(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Absolute.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(100.dp)
                .height(2.dp)
                .background(
                    color = FinFlowTheme.colorScheme.background.inverted,
                    shape = FinFlowTheme.shapes.extraSmall
                )
        )
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = stringResource(R.string.or),
            color = FinFlowTheme.colorScheme.text.primary
        )
        Box(
            modifier = Modifier
                .width(100.dp)
                .height(2.dp)
                .background(
                    color = FinFlowTheme.colorScheme.background.inverted,
                    shape = FinFlowTheme.shapes.extraSmall
                )
        )
    }
}