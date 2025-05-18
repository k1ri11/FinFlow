package ru.mirea.event.event_list.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import ru.mirea.core.navigation.navigator.Navigator
import ru.mirea.core.navigation.screens.Screens
import ru.mirea.core.presentation.AppScaffold
import ru.mirea.core.util.UiHandler
import ru.mirea.core.util.collectInLaunchedEffect
import ru.mirea.core.util.useBy
import ru.mirea.event.event_list.domain.Event
import ru.mirea.event.event_list.presentation.EventsEvent.LoadEvents
import ru.mirea.event.event_list.presentation.widgets.EventItem
import ru.mirea.event.event_list.presentation.widgets.ListHeader
import ru.mirea.uikit.components.money_bar.MoneyProgressBar
import ru.mirea.uikit.components.top_bar.CommonTopBar
import ru.mirea.uikit.theme.FinFlowTheme
import ru.mirea.uikit.utils.appNavigationBarPaddings
import ru.mirea.uikit.utils.systemNavigationPaddings

@Composable
fun EventsListScreen(
    modifier: Modifier = Modifier,
    holder: UiHandler<EventsState, EventsEvent, EventsEffect>,
    onNavigateAdd: () -> Unit,
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
        topBar = { CommonTopBar("Главная") },
    ) { paddingValues ->
        EventsScreenContent(paddingValues, state.events, onNavigateAdd)
    }
}

@Composable
fun EventsScreenContent(
    paddingValues: PaddingValues,
    events: List<Event>,
    onNavigateAdd: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { Spacer(Modifier.height(paddingValues.calculateTopPadding())) }
        item { MoneyProgressBar(600, 300) }
        item {
            ListHeader(
                onAddEventClick = onNavigateAdd
            )
        }
        items(events) { item ->
            EventItem(
                modifier = Modifier.animateItem(),
                event = item,
                onClick = {})
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
        onNavigateAdd = { navigator.navigate(Screens.AddEvent.route) })
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
            onNavigateAdd = {}
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