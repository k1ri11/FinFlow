package ru.mirea.event.details.presentation

import ru.mirea.event.details.domain.model.DetailsDebt
import ru.mirea.event.details.domain.model.EventActivity
import ru.mirea.event.details.domain.model.Transaction
import ru.mirea.event.details.presentation.widgets.CardData
import ru.mirea.uikit.components.money_bar.GroupTabs

// State для экрана деталей события


data class EventDetailsState(
    val eventId: Int = -1,
    val cardData: CardData = CardData(0, 0, "", emptyList()),
    val selectedTab: GroupTabs = GroupTabs.ACTIVITY,
    val activityItems: List<EventActivity> = emptyList(),
    val transactions: List<Transaction> = emptyList(),
    val membersItems: List<Any> = emptyList(),
    val balancesItems: List<DetailsDebt> = emptyList(), // сюда будут приходить балансы
    val isLoading: Boolean = false,
    val error: String? = null,
)

// Event для экрана деталей события
sealed interface EventDetailsEvent {
    data class LoadDetails(val eventId: Int) : EventDetailsEvent
    data class TabSelected(val tab: GroupTabs) : EventDetailsEvent
    data object Action1Clicked : EventDetailsEvent // первая кнопка
    data object Action2Clicked : EventDetailsEvent // вторая кнопка
    data object Action3Clicked : EventDetailsEvent // третья кнопка
    data class LoadActivities(val eventId: Int) : EventDetailsEvent
    data class LoadTransactions(val eventId: Int) : EventDetailsEvent
}

// Effect для экрана деталей события
sealed interface EventDetailsEffect {
    data class ShowError(val message: String) : EventDetailsEffect
} 