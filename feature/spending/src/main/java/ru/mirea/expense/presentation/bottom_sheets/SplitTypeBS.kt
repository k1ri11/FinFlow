package ru.mirea.expense.presentation.bottom_sheets

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.mirea.expense.presentation.SplitType
import ru.mirea.uikit.AppBottomSheet
import ru.mirea.uikit.R
import ru.mirea.uikit.theme.FinFlowTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplitTypeBS(
    onDismiss: () -> Unit,
    onSplitTypeSelected: (SplitType) -> Unit,
) {
    AppBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ) { hide ->
        SplitTypesBSContent(onSplitTypeSelected, hide)
    }
}

@Composable
private fun SplitTypesBSContent(
    onSplitTypeSelected: (SplitType) -> Unit,
    onDismiss: () -> Unit,
) {
    Column {
        SplitType.entries.forEach { item ->
            SplitTypeItem(
                item = item,
                onItemSelected = {
                    onSplitTypeSelected(it)
                    onDismiss()
                }
            )
        }
    }
}

@Composable
private fun SplitTypeItem(
    item: SplitType,
    onItemSelected: (SplitType) -> Unit,
    modifier: Modifier = Modifier,
) {
    val title = when (item) {
        SplitType.EQUALLY -> stringResource(R.string.split_equally)
        SplitType.UNEQUALLY -> stringResource(R.string.split_unequally)
        SplitType.PERCENTAGE -> stringResource(R.string.split_percentage)
        SplitType.SHARES -> stringResource(R.string.split_shares)
    }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onItemSelected(item) }
    ) {
        Text(
            text = title,
            style = FinFlowTheme.typography.bodyMedium,
            color = FinFlowTheme.colorScheme.text.primary,
            modifier = Modifier
                .padding(16.dp)
        )
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
    showBackground = true
)
@Composable
private fun SplitTypesBSContentPreviewLight() {
    FinFlowTheme {
        SplitTypesBSContent({}, {})
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun SplitTypesBSContentPreviewDark() {
    FinFlowTheme {
        SplitTypesBSContent({}, {})
    }
}