package ru.mirea.event.add_event.presentation

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import kotlinx.coroutines.flow.MutableSharedFlow
import ru.mirea.core.presentation.AppScaffold
import ru.mirea.core.util.UiHandler
import ru.mirea.core.util.useBy
import ru.mirea.event.add_event.presentation.AddEventState.Category
import ru.mirea.event.add_event.presentation.select_friends.SelectFriendsBS
import ru.mirea.event.add_event.presentation.widgets.CategoriesRow
import ru.mirea.event.add_event.presentation.widgets.FriendItem
import ru.mirea.uikit.R
import ru.mirea.uikit.components.buttons.FilledButton
import ru.mirea.uikit.components.inputs.CommonEditTextField
import ru.mirea.uikit.components.top_bar.CommonTopBar
import ru.mirea.uikit.theme.FinFlowTheme
import ru.mirea.uikit.utils.appNavigationBarPaddings
import ru.mirea.uikit.utils.systemNavigationPaddings

@Composable
fun AddEventScreen(
    holder: UiHandler<AddEventState, AddEventEvent, AddEventEffect>,
    modifier: Modifier = Modifier,
) {
    val (state, event, effect) = holder
    var showSelectFriendsBS by remember { mutableStateOf(false) }

    AppScaffold(
        modifier = modifier,
        topBar = {
            CommonTopBar(
                modifier = Modifier.padding(horizontal = 16.dp),
                title = "Создать событие",
                leftIconId = R.drawable.ic_arrow_back,
                onLeftIconClick = {},
                rightIconId = R.drawable.ic_person_add,
                onRightIconClick = { showSelectFriendsBS = true }
            )
        },
        floatingActionButton = {
            FilledButton(
                modifier = Modifier.padding(horizontal = 16.dp),
                label = "Создать событие",
                onClick = { /* event создания события */ }
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        AddEventScreenContent(paddingValues, state, event)
    }

    if (showSelectFriendsBS) {
        SelectFriendsBS(
            onDismiss = { showSelectFriendsBS = false },
            onSave = { members ->
                event(AddEventEvent.UpdateMembers(members))
                showSelectFriendsBS = false
            }
        )
    }
}

@Composable
fun AddEventScreenContent(
    paddingValues: PaddingValues,
    state: AddEventState,
    event: (AddEventEvent) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        item { Spacer(Modifier.height(paddingValues.calculateTopPadding())) }

        item {
            AsyncImage(
                modifier = Modifier
                    .size(100.dp)
                    .clickable { /* выбрать картинку */ },
                model = state.icon,
                contentDescription = null,
            )
        }
        item {
            CommonEditTextField(
                label = "Название события",
                value = state.name,
                onValueChange = { event(AddEventEvent.NameChanged(it)) }
            )
        }

        item {
            CommonEditTextField(
                label = "Описание события",
                value = state.description,
                onValueChange = { event(AddEventEvent.DescriptionChanged(it)) }
            )
        }

        item {
            CategoriesRow(
                categories = state.categories,
                onCategorySelect = { event(AddEventEvent.CategoryChanged(it)) },
                selectedCategoryId = state.categoryId
            )
        }

        if (state.members.friends.isNotEmpty()) {
            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateItem(),
                    text = "Друзья",
                    style = MaterialTheme.typography.titleMedium,
                    color = FinFlowTheme.colorScheme.text.primary
                )
            }
        }
        items(state.members.friends) { friend ->
            FriendItem(friend)
        }

        if (state.members.dummiesNames.isNotEmpty()) {
            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateItem(),
                    text = "Временные друзья",
                    style = MaterialTheme.typography.titleMedium,
                    color = FinFlowTheme.colorScheme.text.primary
                )
            }
        }
        items(state.members.dummiesNames) { dummy ->
            Box(
                modifier = Modifier
                    .animateItem()
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(FinFlowTheme.shapes.large)
                    .background(FinFlowTheme.colorScheme.background.secondary)
                    .padding(vertical = 8.dp, horizontal = 16.dp),
            ) {
                Text(
                    text = dummy,
                    style = FinFlowTheme.typography.bodyMedium,
                    color = FinFlowTheme.colorScheme.text.primary
                )
            }
        }

        appNavigationBarPaddings()
        systemNavigationPaddings()
    }
}

@Composable
fun AddEventNavScreen() {
    val viewModel: AddEventViewModel = hiltViewModel()
    val holder = useBy(viewModel)
    AddEventScreen(holder)
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun AddEventScreenPreviewLight() {
    FinFlowTheme {
        AddEventScreen(
            UiHandler(
                state = AddEventState(
                    name = "Darcy Underwood",
                    description = "sgsdw hsdfd",
                    icon = null,
                    categories = listOf(
                        Category(id = 0, name = "Клубешник", iconId = ""),
                        Category(id = 0, name = "Орешник", iconId = ""),
                        Category(id = 0, name = "Бухарешник", iconId = ""),
                    ),
                    categoryId = null,
                ),
                dispatch = {},
                effectFlow = MutableSharedFlow()
            )

        )
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun AddEventScreenPreviewDark() {
    FinFlowTheme {
        AddEventScreenPreviewLight()
    }
}
