package ru.mirea.event.event_list.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.mirea.core.presentation.AppScaffold
import ru.mirea.event.event_list.presentation.widgets.ListHeader
import ru.mirea.uikit.components.money_bar.MoneyProgressBar
import ru.mirea.uikit.components.top_bar.CommonTopBar
import ru.mirea.uikit.theme.FinFlowTheme
import ru.mirea.uikit.utils.appNavigationBarPaddings
import ru.mirea.uikit.utils.systemNavigationPaddings


@Composable
fun EventsListScreen(
    modifier: Modifier = Modifier,
) {

    AppScaffold(
        modifier = modifier,
        topBar = { CommonTopBar("Главная") }
    ) { paddingValues ->
        EventsScreenContent(paddingValues)
    }


}

@Composable
fun EventsScreenContent(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { Spacer(Modifier.height(paddingValues.calculateTopPadding())) }

        item { MoneyProgressBar(600, 300) }

        item { ListHeader() }

//        items() { item -> EventItem(item) }

        appNavigationBarPaddings()
        systemNavigationPaddings()

    }
}

@Composable
fun EventsNavScreen(
    viewModel: EventsViewModel = hiltViewModel(),
) {
//    val holder = useBy(viewModel)
    EventsListScreen()
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun FriendsScreenPreviewLight() {
    FinFlowTheme {
        EventsListScreen()
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun FriendsScreenPreviewDark() {
    FinFlowTheme {
        EventsListScreen()
    }
}