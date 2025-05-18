package ru.mirea.event.details.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.mirea.core.navigation.navigator.Navigator
import ru.mirea.core.presentation.AppScaffold
import ru.mirea.event.details.presentation.widgets.CardData
import ru.mirea.event.details.presentation.widgets.DetailsTopCard
import ru.mirea.uikit.R
import ru.mirea.uikit.components.top_bar.CommonTopBar
import ru.mirea.uikit.utils.systemNavigationPaddings


@Composable
fun EventsDetailsListScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
//    val (state, event, effect) = holder

//    LaunchedEffect(Unit) {
//        event(LoadEvents)
//    }

//    effect.collectInLaunchedEffect { effect ->
//        when (effect) {
//            is EventsEffect.ShowError -> {}
//        }
//    }

    AppScaffold(
        modifier = modifier,
        topBar = {
            CommonTopBar(
                modifier = Modifier.padding(horizontal = 16.dp),
                leftIconId = R.drawable.ic_arrow_back,
                onLeftIconClick = navigateBack,
                title = "Название события",
                rightIconId = R.drawable.ic_edit
            )
        },
    ) { paddingValues ->
        EventsDetailsScreenContent(paddingValues)
    }

}

@Composable
fun EventsDetailsScreenContent(
    paddingValues: PaddingValues,
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
                CardData(
                    oweMoney = 2280,
                    alreadyOwed = 1620,
                    iconUrl = "",
                    membersIconUrls = listOf("", "", "")
                ),
                onTabSelect = {},
            )
        }


        systemNavigationPaddings()
    }

}

@Composable
fun EventsDetailsNavScreen(
    navigator: Navigator,
) {
//    val viewModel: EventsViewModel = hiltViewModel()
//    val holder = useBy(viewModel)
    EventsDetailsListScreen(
//        holder = holder,
        navigateBack = { navigator.popBackStack() })
}