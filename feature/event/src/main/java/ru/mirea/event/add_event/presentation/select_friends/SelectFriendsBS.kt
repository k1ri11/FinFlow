package ru.mirea.event.add_event.presentation.select_friends

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.flow.MutableSharedFlow
import ru.mirea.core.util.collectInLaunchedEffect
import ru.mirea.core.util.useBy
import ru.mirea.event.add_event.domain.models.Members
import ru.mirea.event.add_event.presentation.select_friends.SelectFriendsEffect.Saved
import ru.mirea.event.add_event.presentation.select_friends.SelectFriendsEvent.SelectFriend
import ru.mirea.event.add_event.presentation.select_friends.SelectFriendsEvent.UnselectFriend
import ru.mirea.friends_api.Friend
import ru.mirea.uikit.AppBottomSheet
import ru.mirea.uikit.R
import ru.mirea.uikit.components.buttons.FilledButton
import ru.mirea.uikit.components.buttons.SmallOutlinedButton
import ru.mirea.uikit.components.icon.FFIconButton
import ru.mirea.uikit.components.items.SelectableFriendItem
import ru.mirea.uikit.components.top_bar.CommonTopBar
import ru.mirea.uikit.theme.FinFlowTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectFriendsBS(
    onDismiss: () -> Unit,
    onSave: (Members) -> Unit,
) {
    val viewModel: SelectFriendsViewModel = hiltViewModel()
    val lazyPagingItems = viewModel.friendsPagingData.collectAsLazyPagingItems()
    val (state, event, effect) = useBy(viewModel)

    effect.collectInLaunchedEffect { friendEffect ->
        when (friendEffect) {
            is Saved -> onSave(friendEffect.members)
        }
    }

    AppBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ) { hide ->
        SelectFriendsScreenContent(
            modifier = Modifier.fillMaxSize(),
            lazyPagingItems = lazyPagingItems,
            state = state,
            event = event,
            onDismiss = hide
        )
    }
}

@Composable
fun SelectFriendsScreenContent(
    modifier: Modifier = Modifier,
    lazyPagingItems: LazyPagingItems<Friend>,
    state: SelectFriendsState,
    event: (SelectFriendsEvent) -> Unit,
    onDismiss: () -> Unit,
) {
    var editName by remember(state.editDialogName, state.showEditDialog) {
        mutableStateOf(
            TextFieldValue(state.editDialogName)
        )
    }

    if (state.showEditDialog && state.editDialogIndex != null) {
        AlertDialog(
            onDismissRequest = {},
            title = {
                Text(
                    stringResource(R.string.enter_friend_name),
                    style = FinFlowTheme.typography.titleMedium
                )
            },
            text = {
                OutlinedTextField(
                    value = editName,
                    onValueChange = { editName = it },
                    label = { Text(stringResource(R.string.name)) }
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    event(
                        SelectFriendsEvent.TempFriendNameChanged(
                            state.editDialogIndex,
                            editName.text
                        )
                    )
                }) {
                    Text(stringResource(R.string.save))
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    event(SelectFriendsEvent.CancelEditDialog)
                }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }

    // Диалог для нового друга
    if (state.showNewFriendDialog) {
        AlertDialog(
            onDismissRequest = { event(SelectFriendsEvent.CancelNewFriendDialog) },
            title = {
                Text(
                    stringResource(R.string.enter_name),
                    style = FinFlowTheme.typography.titleMedium
                )
            },
            text = {
                OutlinedTextField(
                    value = state.newFriendName,
                    onValueChange = { event(SelectFriendsEvent.NewTempFriendNameChanged(it)) },
                    label = { Text(stringResource(R.string.name)) }
                )
            },
            confirmButton = {
                TextButton(onClick = { event(SelectFriendsEvent.ConfirmAddTempFriend) }) {
                    Text(stringResource(R.string.add))
                }
            },
            dismissButton = {
                TextButton(onClick = { event(SelectFriendsEvent.CancelNewFriendDialog) }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }

    Column(modifier = Modifier.background(FinFlowTheme.colorScheme.background.primary)) {
        CommonTopBar(
            title = "Выбор друзей",
            rightIconId = R.drawable.ic_close,
            onRightIconClick = onDismiss
        )

        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateItem(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.temp_friends),
                        style = MaterialTheme.typography.titleMedium,
                        color = FinFlowTheme.colorScheme.text.primary
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    SmallOutlinedButton(
                        label = stringResource(R.string.add),
                        iconId = R.drawable.ic_plus,
                        onClick = { event(SelectFriendsEvent.AddTempFriend) }
                    )
                }
            }
            itemsIndexed(state.tempFriends) { index, friend ->
                Row(
                    modifier = Modifier
                        .animateItem()
                        .fillMaxWidth()
                        .clip(FinFlowTheme.shapes.large)
                        .background(FinFlowTheme.colorScheme.background.secondary)
                        .clickable { event(SelectFriendsEvent.EditTempFriend(index)) }
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = friend.name,
                        style = FinFlowTheme.typography.bodyMedium,
                        color = FinFlowTheme.colorScheme.text.primary
                    )
                    FFIconButton(
                        onClick = { event(SelectFriendsEvent.RemoveTempFriend(index)) },
                        resId = R.drawable.ic_delete
                    )
                }
            }
            item {
                HorizontalDivider(
                    modifier = Modifier.animateItem(),
                    thickness = 2.dp,
                    color = FinFlowTheme.colorScheme.background.secondary
                )
            }
            item {
                Text(
                    modifier = Modifier.animateItem(),
                    text = stringResource(R.string.user_friends),
                    style = MaterialTheme.typography.titleMedium,
                    color = FinFlowTheme.colorScheme.text.primary
                )
            }
            items(
                count = lazyPagingItems.itemCount,
            ) { index ->
                val friend = lazyPagingItems[index]
                friend?.let {
                    SelectableFriendItem(
                        name = friend.name,
                        icon = friend.icon,
                        isSelected = state.selectedFriends.contains(friend),
                        modifier = Modifier
                            .animateItem()
                            .clickable {
                                if (state.selectedFriends.contains(friend)) {
                                    event(UnselectFriend(friend))
                                } else {
                                    event(SelectFriend(friend))
                                }
                            },
                        onSelect = {
                            if (state.selectedFriends.contains(friend)) {
                                event(UnselectFriend(friend))
                            } else {
                                event(SelectFriend(friend))
                            }
                        }
                    )
                }
            }

            item {
                FilledButton(
                    modifier = Modifier.animateItem(),
                    label = stringResource(R.string.save),
                    onClick = {
                        onDismiss()
                        event(SelectFriendsEvent.Save)
                    }
                )
            }
        }
    }

}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun SelectFriendsScreenPreviewLight() {
    FinFlowTheme {
        SelectFriendsScreenContent(
            state = SelectFriendsState(),
            lazyPagingItems = MutableSharedFlow<PagingData<Friend>>().collectAsLazyPagingItems(),
            event = {},
            onDismiss = {}
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun SelectFriendsScreenPreviewDark() {
    FinFlowTheme {
        SelectFriendsScreenPreviewLight()
    }
} 