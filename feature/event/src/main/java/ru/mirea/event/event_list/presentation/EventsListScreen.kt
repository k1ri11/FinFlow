package ru.mirea.event.event_list.presentation

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import ru.mirea.core.navigation.Screens
import ru.mirea.core.navigation.navigator.Navigator
import ru.mirea.core.util.UiHandler
import ru.mirea.core.util.collectInLaunchedEffect
import ru.mirea.core.util.useBy
import ru.mirea.event.event_list.domain.Event
import ru.mirea.event.event_list.presentation.EventsEvent.LoadEvents
import ru.mirea.event.event_list.presentation.widgets.EventItem
import ru.mirea.event.event_list.presentation.widgets.ListHeader
import ru.mirea.uikit.AppScaffold
import ru.mirea.uikit.R
import ru.mirea.uikit.components.top_bar.CommonTopBar
import ru.mirea.uikit.theme.FinFlowTheme
import ru.mirea.uikit.utils.appNavigationBarPaddings
import ru.mirea.uikit.utils.systemNavigationPaddings

@Composable
fun EventsListScreen(
    modifier: Modifier = Modifier,
    holder: UiHandler<EventsState, EventsEvent, EventsEffect>,
    navigateAdd: () -> Unit,
    navigateDetails: (Int) -> Unit,
) {
    val (state, event, effect) = holder

    LaunchedEffect(Unit) {
        event(LoadEvents)
    }

    effect.collectInLaunchedEffect { effect ->
        when (effect) {
            is EventsEffect.ShowError -> {}
        }
    }

    AppScaffold(
        modifier = modifier,
        topBar = { CommonTopBar(stringResource(R.string.main)) },
    ) { paddingValues ->
        EventsScreenContent(paddingValues, state.events, navigateAdd, navigateDetails)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EventsScreenContent(
    paddingValues: PaddingValues,
    events: List<Event>,
    navigateAdd: () -> Unit,
    navigateDetails: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { Spacer(Modifier.height(paddingValues.calculateTopPadding())) }
        stickyHeader {
            ListHeader(
                onAddEventClick = navigateAdd
            )
        }
        items(events) { item ->
            EventItem(
                modifier = Modifier.animateItem(),
                event = item,
                onClick = { navigateDetails(item.id) }
            )
        }

        appNavigationBarPaddings()
        systemNavigationPaddings()
    }
}

@Composable
fun EventsNavScreen(
    navigator: Navigator,
) {
    val viewModel: EventsViewModel = hiltViewModel()
    val holder = useBy(viewModel)
    EventsListScreen(
        holder = holder,
        navigateAdd = { navigator.navigate(Screens.AddEvent.route) },
        navigateDetails = { eventId -> navigator.navigate(Screens.EventDetails.createRoute(eventId)) }
    )
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun FriendsScreenPreviewLight() {
    FinFlowTheme {
        EventsListScreen(
            holder = UiHandler(
                state = EventsState(), dispatch = { }, effectFlow = MutableSharedFlow()
            ),
            navigateAdd = {},
            navigateDetails = {}
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun FriendsScreenPreviewDark() {
    FriendsScreenPreviewLight()
}