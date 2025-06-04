package ru.mirea.event.details.presentation

import ru.mirea.event.details.domain.model.DetailsDebt
import ru.mirea.event.details.domain.model.Transaction
import ru.mirea.event.details.presentation.widgets.CardData
import ru.mirea.uikit.components.money_bar.GroupTabs

// State для экрана деталей события
data class EventDetailsState(
    val eventId: Int = -1,
    val cardData: CardData = CardData(0f, 0f, "", emptyList()),
    val selectedTab: GroupTabs = GroupTabs.TRANSACTIONS,
    val optimizedDebts: List<DetailsDebt> = emptyList(),
    val transactions: List<Transaction> = emptyList(),
    val membersItems: List<Any> = emptyList(),
    val debts: List<DetailsDebt> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val showOnlyMine: Boolean = false,
    val myOweSum: Float = 0f,
    val oweToMeSum: Float = 0f,
)

// Event для экрана деталей события
sealed interface EventDetailsEvent {
    data class LoadDetails(val eventId: Int) : EventDetailsEvent
    data class TabSelected(val tab: GroupTabs) : EventDetailsEvent
}

// Effect для экрана деталей события
sealed interface EventDetailsEffect {
    data class ShowError(val message: String) : EventDetailsEffect
} 