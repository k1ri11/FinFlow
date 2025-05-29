package ru.mirea.event.details.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.mirea.core.util.BaseViewModel
import ru.mirea.event.details.data.repository.DetailsRepository
import ru.mirea.event.details.domain.model.EventActivity
import ru.mirea.event.details.domain.toDomain
import ru.mirea.event.details.presentation.widgets.CardData
import ru.mirea.uikit.components.money_bar.GroupTabs
import javax.inject.Inject

@HiltViewModel
class EventDetailsViewModel @Inject constructor(
    private val detailsRepository: DetailsRepository,
) : BaseViewModel<EventDetailsState, EventDetailsEvent, EventDetailsEffect>(EventDetailsState()) {

    override fun event(event: EventDetailsEvent) {
        when (event) {
            is EventDetailsEvent.LoadDetails -> {
                updateState { it.copy(eventId = event.eventId) }
                loadDetails(event.eventId)
                loadDebts(event.eventId)
                loadActivities(event.eventId)
            }

            is EventDetailsEvent.TabSelected -> {
                updateState { it.copy(selectedTab = event.tab) }
                if (event.tab == GroupTabs.ACTIVITY) {
                    val eventId = state.value.eventId
                    loadActivities(eventId)
                }
                if (event.tab == GroupTabs.TRANSACTIONS) {
                    val eventId = state.value.eventId
                    loadTransactions(eventId)
                }
            }

            is EventDetailsEvent.Action1Clicked -> {
                fakeRequestForAction(1)
            }

            is EventDetailsEvent.Action2Clicked -> {
                fakeRequestForAction(2)
            }

            is EventDetailsEvent.Action3Clicked -> {
                fakeRequestForAction(3)
            }

            is EventDetailsEvent.LoadActivities -> {
                loadActivities(event.eventId)
            }

            is EventDetailsEvent.LoadTransactions -> {
                loadTransactions(event.eventId)
            }
        }
    }

    private fun loadDetails(eventId: Int) {
        updateState { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            delay(500) // имитация запроса
            updateState {
                it.copy(
                    cardData = CardData(
                        oweMoney = 2280,
                        alreadyOwed = 1620,
                        iconUrl = "",
                        membersIconUrls = listOf("", "", "")
                    ),
                    isLoading = false
                )
            }
        }
    }

    private fun loadDebts(eventId: Int) {
        updateState { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            val result = detailsRepository.getDebts(eventId)
            result.onSuccess { debts ->
                updateState { it.copy(balancesItems = debts, isLoading = false) }
            }.onFailure { err ->
                updateState { it.copy(isLoading = false) }
                emitEffect(EventDetailsEffect.ShowError(err.message ?: "Ошибка загрузки балансов"))
            }
        }
    }

    private fun loadActivities(eventId: Int) {
        updateState { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            val result = detailsRepository.getActivities(eventId)
            result.onSuccess { activities ->
                updateState {
                    it.copy(
                        activityItems = activities.map { dto ->
                            EventActivity(
                                activityId = dto.activityId,
                                description = dto.description,
                                iconId = dto.iconId,
                                datetime = dto.datetime
                            )
                        },
                        isLoading = false
                    )
                }
            }.onFailure { err ->
                updateState { it.copy(isLoading = false) }
                emitEffect(
                    EventDetailsEffect.ShowError(
                        err.message ?: "Ошибка загрузки активностей"
                    )
                )
            }
        }
    }

    private fun loadTransactions(eventId: Int) {
        updateState { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            val result = detailsRepository.getTransactions(eventId)
            result.onSuccess { transactions ->
                updateState {
                    it.copy(
                        transactions = transactions.map { dto -> dto.toDomain() },
                        isLoading = false
                    )
                }
            }.onFailure { err ->
                updateState { it.copy(isLoading = false) }
                emitEffect(
                    EventDetailsEffect.ShowError(
                        err.message ?: "Ошибка загрузки транзакций"
                    )
                )
            }
        }
    }

    private fun fakeRequestForAction(action: Int) {
        updateState { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            delay(400)
            updateState {
                it.copy(
//                    activityItems = listOf(
//                        "Action $action result 1",
//                        "Action $action result 2"
//                    ), isLoading = false
                )
            }
        }
    }
} 