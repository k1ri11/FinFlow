package ru.mirea.event.add_event.presentation

import android.content.res.Configuration
import androidx.compose.foundation.background
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import kotlinx.coroutines.flow.MutableSharedFlow
import ru.mirea.core.navigation.navigator.Navigator
import ru.mirea.core.util.UiHandler
import ru.mirea.core.util.collectInLaunchedEffect
import ru.mirea.core.util.useBy
import ru.mirea.event.add_event.domain.models.Category
import ru.mirea.event.add_event.domain.models.CategoryIcon
import ru.mirea.event.add_event.presentation.AddEventEvent.CreateEvent
import ru.mirea.event.add_event.presentation.select_friends.SelectFriendsBS
import ru.mirea.event.add_event.presentation.widgets.CategoriesRow
import ru.mirea.event.add_event.presentation.widgets.FriendItem
import ru.mirea.uikit.AppScaffold
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
    onNavigateBack: () -> Unit,
) {
    val (state, event, effect) = holder
    var showSelectFriendsBS by remember { mutableStateOf(false) }

    AppScaffold(
        modifier = modifier,
        topBar = {
            CommonTopBar(
                modifier = Modifier.padding(horizontal = 16.dp),
                title = stringResource(R.string.create_event),
                leftIconId = R.drawable.ic_arrow_back,
                onLeftIconClick = onNavigateBack,
                rightIconId = R.drawable.ic_person_add,
                onRightIconClick = { showSelectFriendsBS = true }
            )
        },
        floatingActionButton = {
            FilledButton(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                label = stringResource(R.string.create_event),
                onClick = { event(CreateEvent) }
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

    effect.collectInLaunchedEffect { addEventEffect ->
        when (addEventEffect) {
            AddEventEffect.Success -> onNavigateBack()
            else -> {}
        }
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
                    .size(100.dp),
                model = state.icon,
                contentDescription = null,
                placeholder = painterResource(R.drawable.placeholder_money),
                error = painterResource(R.drawable.placeholder_money)
            )
        }
        item {
            CommonEditTextField(
                label = stringResource(R.string.event_name),
                value = state.name,
                onValueChange = { event(AddEventEvent.NameChanged(it)) }
            )
        }

        item {
            CommonEditTextField(
                label = stringResource(R.string.event_description),
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
                    text = stringResource(R.string.friends),
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
                    text = stringResource(R.string.temp_friends),
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
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
fun AddEventNavScreen(
    navigator: Navigator,
) {
    val viewModel: AddEventViewModel = hiltViewModel()
    val holder = useBy(viewModel)
    AddEventScreen(holder,
        onNavigateBack = { navigator.popBackStack() }
    )
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
                        Category(
                            id = 0, name = "Клубешник", CategoryIcon(
                                id = 8007,
                                name = "Julia Rollins",
                                externalUuid = "nunc"
                            )
                        ),
                        Category(
                            id = 1, name = "Орешник", CategoryIcon(
                                id = 8007,
                                name = "Julia Rollins",
                                externalUuid = "nunc"
                            )
                        ),
                        Category(
                            id = 2, name = "Бухарешник", CategoryIcon(
                                id = 8007,
                                name = "Julia Rollins",
                                externalUuid = "nunc"
                            )
                        ),
                    ),
                    categoryId = null,
                ),
                dispatch = {},
                effectFlow = MutableSharedFlow()
            ),
            onNavigateBack = {}
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
