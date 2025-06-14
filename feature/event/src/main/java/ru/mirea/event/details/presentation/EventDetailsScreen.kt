package ru.mirea.event.details.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FabPosition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.mirea.core.navigation.Screens.AddSpending.createRoute
import ru.mirea.core.navigation.navigator.Navigator
import ru.mirea.core.util.UiHandler
import ru.mirea.core.util.useBy
import ru.mirea.event.details.presentation.EventDetailsEvent.TabSelected
import ru.mirea.event.details.presentation.widgets.DebtItem
import ru.mirea.event.details.presentation.widgets.DetailsTopCard
import ru.mirea.event.details.presentation.widgets.TransactionItem
import ru.mirea.uikit.AppScaffold
import ru.mirea.uikit.R
import ru.mirea.uikit.components.buttons.FilledButton
import ru.mirea.uikit.components.money_bar.GroupTabs
import ru.mirea.uikit.components.top_bar.CommonTopBar
import ru.mirea.uikit.utils.systemNavigationPaddings

@Composable
fun EventsDetailsListScreen(
    holder: UiHandler<EventDetailsState, EventDetailsEvent, EventDetailsEffect>,
    navigateBack: () -> Unit,
    eventId: Int,
    navigator: Navigator,
    modifier: Modifier = Modifier,
) {
    val (state, event, effect) = holder

    LaunchedEffect(eventId) {
        event(EventDetailsEvent.LoadDetails(eventId))
    }

    AppScaffold(
        modifier = modifier,
        topBar = {
            CommonTopBar(
                modifier = Modifier.padding(horizontal = 16.dp),
                leftIconId = R.drawable.ic_arrow_back,
                onLeftIconClick = navigateBack,
                title = stringResource(R.string.event_title),
                rightIconId = R.drawable.ic_edit
            )
        },
        floatingActionButton = {
            FilledButton(
                modifier = Modifier.padding(horizontal = 16.dp),
                label = stringResource(R.string.add_new_spending),
                onClick = {
                    navigator.navigate(createRoute(eventId))
                },
                iconId = R.drawable.ic_plus,
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        EventsDetailsScreenContent(
            paddingValues = paddingValues,
            state = state,
            onTabSelect = { tab -> event(TabSelected(tab)) },
        )
    }
}

@Composable
fun EventsDetailsScreenContent(
    paddingValues: PaddingValues,
    state: EventDetailsState,
    onTabSelect: (GroupTabs) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { Spacer(Modifier.height(paddingValues.calculateTopPadding())) }
        item {
            DetailsTopCard(
                cardData = state.cardData.copy(
                    oweMoney = state.oweToMeSum,
                    alreadyOwed = state.myOweSum
                ),
                onTabSelect = { index ->
                    val tab = GroupTabs.entries.toTypedArray()[index]
                    onTabSelect(tab)
                },
                selectedTab = state.selectedTab
            )
        }
        when (state.selectedTab) {
            GroupTabs.TRANSACTIONS -> items(state.transactions) {
                TransactionItem(
                    transaction = it,
                    modifier = Modifier.animateItem()
                )
            }

            GroupTabs.BALANCES -> debtsItems(state)
            GroupTabs.OPT_BALANCES -> optimizedDebtsItems(state)
        }
        item { Spacer(modifier = Modifier.height(64.dp)) }
        systemNavigationPaddings()
    }
}

fun LazyListScope.debtsItems(state: EventDetailsState) {
    items(state.debts) { DebtItem(debtItem = it, modifier = Modifier.animateItem()) }
}

fun LazyListScope.optimizedDebtsItems(state: EventDetailsState) {
    items(state.optimizedDebts) { DebtItem(debtItem = it, modifier = Modifier.animateItem()) }
}

@Composable
fun EventsDetailsNavScreen(
    navigator: Navigator,
    eventId: Int,
) {
    val viewModel: EventDetailsViewModel = hiltViewModel()
    val holder = useBy(viewModel)
    EventsDetailsListScreen(
        holder = holder,
        navigateBack = { navigator.popBackStack() },
        eventId = eventId,
        navigator = navigator
    )
}