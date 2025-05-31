package ru.mirea.expense.presentation.bottom_sheets.select_friend

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import ru.mirea.core.util.useBy
import ru.mirea.expense.domain.model.EventUser
import ru.mirea.expense.domain.model.UserProfile
import ru.mirea.expense.presentation.bottom_sheets.select_friend.SelectFriendEvent.Load
import ru.mirea.uikit.AppBottomSheet
import ru.mirea.uikit.components.buttons.FilledButton
import ru.mirea.uikit.theme.FinFlowTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParticipantsBS(
    eventId: Int,
    selectedUsers: List<EventUser>,
    onDismiss: () -> Unit,
    onSave: (List<EventUser>) -> Unit,
) {
    val viewModel: SelectFriendViewModel = hiltViewModel()
    val (state, event, _) = useBy(viewModel)
    var selected by remember(selectedUsers) { mutableStateOf(selectedUsers.map { it.id }.toSet()) }

    LaunchedEffect(Unit) {
        event(Load(eventId))
    }

    AppBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ) { hide ->
        ParticipantsBSContent(
            friends = state.friends,
            selected = selected,
            onSelect = { id ->
                selected = if (selected.contains(id)) selected - id else selected + id
            },
            onSave = {
                val selectedFriends = state.friends.filter { selected.contains(it.id) }
                onSave(selectedFriends)
                hide()
            }
        )
    }
}

@Composable
private fun ParticipantsBSContent(
    friends: List<EventUser>,
    selected: Set<Int>,
    onSelect: (Int) -> Unit,
    onSave: () -> Unit,
) {
    LazyColumn {
        items(friends) { friend ->
            FriendItem(
                friend = friend,
                selected = selected.contains(friend.id),
                onSelect = { onSelect(friend.id) }
            )
        }
        item {
            SaveButton(onSave)
        }
    }
}

@Composable
private fun FriendItem(
    friend: EventUser,
    selected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onSelect() }
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
        RadioButton(
            selected = selected,
            onClick = onSelect
        )
    }
}

@Composable
private fun SaveButton(onSave: () -> Unit) {
    FilledButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        label = "Сохранить",
        onClick = onSave
    )
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun ParticipantsBottomSheetPreviewLight() {
    FinFlowTheme {
        ParticipantsBSContent(
            friends = listOf(
                EventUser(1, "", false, UserProfile(1, "Иван", null, null)),
                EventUser(2, "", false, UserProfile(2, "Оля", null, null)),
                EventUser(3, "", false, UserProfile(3, "Петя", null, null)),
            ),
            selected = setOf(1, 3),
            onSelect = {},
            onSave = {}
        )
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun ParticipantsBottomSheetPreviewDark() {
    FinFlowTheme {
        ParticipantsBottomSheetPreviewLight()
    }
} 