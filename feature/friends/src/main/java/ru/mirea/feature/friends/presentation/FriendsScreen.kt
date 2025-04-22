package ru.mirea.feature.friends.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.flow.MutableSharedFlow
import ru.mirea.core.presentation.AppScaffold
import ru.mirea.core.util.UiHandler
import ru.mirea.core.util.collectInLaunchedEffect
import ru.mirea.core.util.useBy
import ru.mirea.feature.friends.domain.model.Friend
import ru.mirea.feature.friends.presentation.widgets.FriendItem
import ru.mirea.uikit.R
import ru.mirea.uikit.components.loader.CircularLoader
import ru.mirea.uikit.components.money_bar.MoneyProgressBar
import ru.mirea.uikit.components.top_bar.CommonTopBar
import ru.mirea.uikit.theme.FinFlowTheme
import ru.mirea.uikit.utils.appNavigationBarPaddings
import ru.mirea.uikit.utils.systemNavigationPaddings

@Composable
fun FriendsScreen(
    holder: UiHandler<FriendsState, FriendsEvent, FriendsEffect>,
    pagingFriends: LazyPagingItems<Friend>,
) {
    val (state, event, effect) = holder

    effect.collectInLaunchedEffect { friendsEffect ->
        when (friendsEffect) {
            is FriendsEffect.ShowError -> {}
        }
    }

    AppScaffold(
        topBar = {
            CommonTopBar(
                modifier = Modifier.padding(horizontal = 16.dp),
                title = stringResource(R.string.friends),
            )
        }
    ) { paddingValues ->
        FriendsScreenContent(state, pagingFriends, paddingValues)
    }
}

@Composable
private fun FriendsScreenContent(
    state: FriendsState,
    lazyPagingItems: LazyPagingItems<Friend>,
    paddingValues: PaddingValues,
) {

    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item { Spacer(Modifier.height(paddingValues.calculateTopPadding())) }

        item { Spacer(Modifier.height(16.dp)) }

        item {
            MoneyProgressBar(
                oweMoney = state.owed,
                alreadyOwed = state.payed,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item { Spacer(Modifier.height(8.dp)) }

        items(
            count = lazyPagingItems.itemCount,
//            key = lazyPagingItems.itemKey { it.id }
        ) { index ->
            val friend = lazyPagingItems[index]
            friend?.let { FriendItem(friend) }
        }

        item {
            if (lazyPagingItems.loadState.append is LoadState.Loading) {
                CircularLoader()
            }
        }


        appNavigationBarPaddings()
        systemNavigationPaddings()

    }
}

@Composable
fun FriendsNavScreen(
    viewModel: FriendsViewModel = hiltViewModel(),
) {
    val holder = useBy(viewModel)
    val lazyPagingItems = viewModel.friendsPagingData.collectAsLazyPagingItems()
    FriendsScreen(holder = holder, pagingFriends = lazyPagingItems)
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun FriendsScreenPreviewLight() {
    FinFlowTheme {
        FriendsScreen(
            UiHandler(FriendsState(), {}, MutableSharedFlow()),
            pagingFriends = MutableSharedFlow<PagingData<Friend>>().collectAsLazyPagingItems()
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun FriendsScreenPreviewDark() {
    FinFlowTheme {
        FriendsScreen(
            UiHandler(
                FriendsState(
                    isLoading = false,
                    error = null,
                    owed = 1737,
                    payed = 692
                ),
                {},
                MutableSharedFlow()
            ),
            pagingFriends = MutableSharedFlow<PagingData<Friend>>().collectAsLazyPagingItems()
        )
    }
}