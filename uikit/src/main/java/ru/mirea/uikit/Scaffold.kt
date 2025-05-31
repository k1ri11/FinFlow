package ru.mirea.uikit

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import ru.mirea.uikit.theme.FinFlowTheme

/**
 * Direct passthrough to [Scaffold] but contains a few specific override values. Everything is
 * still overridable if necessary.
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun AppScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = { },
    bottomBar: @Composable () -> Unit = { },
    snackbarHost: @Composable () -> Unit = { },
    floatingActionButton: @Composable () -> Unit = { },
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    pullToRefreshState: AppPullToRefreshState = rememberAppPullToRefreshState(),
    containerColor: Color = FinFlowTheme.colorScheme.background.primary,
    contentColor: Color = FinFlowTheme.colorScheme.text.primary,
    contentWindowInsets: WindowInsets = ScaffoldDefaults
        .contentWindowInsets
        .exclude(WindowInsets.navigationBars),
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .semantics { testTagsAsResourceId = true }
            .then(modifier),
        topBar = topBar,
        bottomBar = bottomBar,
        snackbarHost = snackbarHost,
        floatingActionButton = {
            Box(modifier = Modifier.navigationBarsPadding()) {
                floatingActionButton()
            }
        },
        floatingActionButtonPosition = floatingActionButtonPosition,
        containerColor = containerColor,
        contentColor = contentColor,
        contentWindowInsets = contentWindowInsets,
        content = { paddingValues ->
            val internalPullToRefreshState = rememberPullToRefreshState()
            LaunchedEffect(pullToRefreshState.isRefreshing) {
                if (pullToRefreshState.isRefreshing) {
                    internalPullToRefreshState.animateToHidden()
                }
            }
            if (pullToRefreshState.isEnabled) {
                PullToRefreshBox(
                    isRefreshing = pullToRefreshState.isRefreshing,
                    onRefresh = pullToRefreshState.onRefresh,
                    state = internalPullToRefreshState,
                    indicator = {
                        Indicator(
                            modifier = Modifier.align(Alignment.TopCenter),
                            isRefreshing = pullToRefreshState.isRefreshing,
                            containerColor = containerColor,
                            color = MaterialTheme.colorScheme.primary,
                            state = internalPullToRefreshState
                        )
                    },
                    content = { content(paddingValues) }
                )
            } else {
                content(paddingValues)
            }
        }
    )
}

/**
 * The state of the pull-to-refresh.
 */
data class AppPullToRefreshState(
    val isEnabled: Boolean,
    val isRefreshing: Boolean,
    val onRefresh: () -> Unit,
)

/**
 * Create and remember the default [AppPullToRefreshState].
 * Example:
 * ```
 * val pullToRefreshState = rememberPullToRefreshState(
 *    isEnabled = state.isPullToRefreshEnabled,
 *    isRefreshing = state.isRefreshing,
 *    onRefresh = remember(viewModel) {
 *        { viewModel.trySendAction(VaultAction.RefreshPull) }
 *    }
 * ```
 */

@Composable
fun rememberAppPullToRefreshState(
    isEnabled: Boolean = false,
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit = { },
): AppPullToRefreshState = remember(isEnabled, isRefreshing, onRefresh) {
    AppPullToRefreshState(
        isEnabled = isEnabled,
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
    )
}