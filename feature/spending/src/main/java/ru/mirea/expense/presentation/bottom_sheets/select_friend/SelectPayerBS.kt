package ru.mirea.expense.presentation.bottom_sheets.select_friend

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import ru.mirea.core.util.useBy
import ru.mirea.expense.domain.model.EventUser
import ru.mirea.expense.presentation.bottom_sheets.select_friend.SelectFriendEvent.Load
import ru.mirea.uikit.AppBottomSheet
import ru.mirea.uikit.theme.FinFlowTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectPayerBS(
    eventId: Int,
    onDismiss: () -> Unit,
    onUserSelected: (EventUser) -> Unit,
) {

    val viewModel: SelectFriendViewModel = hiltViewModel()
    val (state, event, effect) = useBy(viewModel)

    LaunchedEffect(Unit) {
        event(Load(eventId))
    }

    AppBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ) { hide ->
        SelectPayerBSContent(state, hide, onUserSelected)
    }
}

@Composable
fun SelectPayerBSContent(
    state: SelectFriendState,
    onDismiss: () -> Unit,
    onUserSelected: (EventUser) -> Unit,
) {
    LazyColumn {
        items(state.friends) { friend ->
            FriendItem(
                friend = friend,
                onUserSelected = {
                    onUserSelected(friend)
                    onDismiss()
                }
            )
        }
    }
}

@Composable
fun FriendItem(
    friend: EventUser,
    onUserSelected: (EventUser) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onUserSelected(friend) }
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AsyncImage(
            model = friend.profile.photo,
            contentDescription = null,
            modifier = Modifier.size(48.dp),
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = Modifier.weight(1f),
            text = friend.profile.nickname,
            style = FinFlowTheme.typography.titleSmall,
            color = FinFlowTheme.colorScheme.text.primary
        )

    }
}