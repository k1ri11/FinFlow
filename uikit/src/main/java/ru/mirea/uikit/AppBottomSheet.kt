package ru.mirea.uikit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ru.mirea.uikit.theme.FinFlowTheme
import ru.mirea.uikit.utils.SystemNavigationPaddings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBottomSheet(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    dragHandle: @Composable (() -> Unit)? = { DragHandleIndicator() },
    showBottomSheet: Boolean = true,
    sheetState: SheetState = rememberModalBottomSheetState(),
    content: @Composable ColumnScope.(onDismiss: () -> Unit) -> Unit,
) {
    if (!showBottomSheet) return
    val coroutineScope = rememberCoroutineScope()
    val onDismiss: () -> Unit = {
        coroutineScope.launch {
            sheetState.hide()
            onDismissRequest()
        }
    }
    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        modifier = modifier.statusBarsPadding(),
        dragHandle = dragHandle,
        sheetState = sheetState,
        shape = FinFlowTheme.shapes.extraLargeTopRounded,
        contentColor = FinFlowTheme.colorScheme.background.primary,
        containerColor = FinFlowTheme.colorScheme.background.primary,
        contentWindowInsets = { WindowInsets.statusBars.only(WindowInsetsSides.Bottom) },
        content = {
            content(onDismiss)
            SystemNavigationPaddings()
        }
    )
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            sheetState.show()
        }
    }
}


@Composable
fun DragHandleIndicator(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .padding(top = 8.dp, bottom = 10.dp)
            .clip(shape = CircleShape)
            .width(width = 40.dp)
            .height(height = 6.dp)
            .background(color = FinFlowTheme.colorScheme.icon.secondary),
    )
}