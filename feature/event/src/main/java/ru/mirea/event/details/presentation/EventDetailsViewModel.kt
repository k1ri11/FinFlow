package ru.mirea.event.details.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.mirea.core.util.BaseViewModel
import ru.mirea.event.details.data.repository.DetailsRepository
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
                loadDetails()
                loadDebts()
            }

            is EventDetailsEvent.TabSelected -> {
                updateState { it.copy(selectedTab = event.tab) }
                fakeRequestForTab(event.tab)
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
        }
    }

    private fun loadDetails() {
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

    private fun loadDebts() {
        updateState { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            val result = detailsRepository.getDebts(4) // todo прокинуть с экрана
            result.onSuccess { debts ->
                updateState { it.copy(balancesItems = debts, isLoading = false) }
            }.onFailure { err ->
                updateState { it.copy(isLoading = false) }
                emitEffect(EventDetailsEffect.ShowError(err.message ?: "Ошибка загрузки балансов"))
            }
        }
    }

    private fun fakeRequestForTab(tab: GroupTabs) {
        updateState { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            delay(400)
            updateState {
                when (tab) {
                    GroupTabs.ACTIVITY -> it.copy(
                        activityItems = listOf(
                            "Activity 1",
                            "Activity 2",
                            "Activity 3"
                        ), isLoading = false
                    )

                    GroupTabs.MEMBERS -> it.copy(
                        membersItems = listOf("Member 1", "Member 2"),
                        isLoading = false
                    )

                    GroupTabs.BALANCES -> it.copy(isLoading = false) // balancesItems уже заполняется отдельно
                }
            }
        }
    }

    private fun fakeRequestForAction(action: Int) {
        updateState { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            delay(400)
            updateState {
                it.copy(
                    activityItems = listOf(
                        "Action $action result 1",
                        "Action $action result 2"
                    ), isLoading = false
                )
            }
        }
    }
} 