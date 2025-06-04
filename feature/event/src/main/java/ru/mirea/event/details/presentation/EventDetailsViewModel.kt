package ru.mirea.event.details.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.mirea.core.service.UserService
import ru.mirea.core.util.BaseViewModel
import ru.mirea.event.details.data.repository.DetailsRepository
import ru.mirea.event.details.domain.toDomain
import ru.mirea.event.details.presentation.widgets.CardData
import javax.inject.Inject

@HiltViewModel
class EventDetailsViewModel @Inject constructor(
    private val detailsRepository: DetailsRepository,
    private val userService: UserService,
) : BaseViewModel<EventDetailsState, EventDetailsEvent, EventDetailsEffect>(EventDetailsState()) {

    override fun event(event: EventDetailsEvent) {
        when (event) {
            is EventDetailsEvent.LoadDetails -> {
                updateState { it.copy(eventId = event.eventId) }
                loadDetails()
                loadDebts(event.eventId)
                loadOptimizedDebts(event.eventId)
                loadTransactions(event.eventId)
            }

            is EventDetailsEvent.TabSelected -> {
                updateState { it.copy(selectedTab = event.tab) }
            }
        }
    }

    private fun loadDetails() {
        updateState { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            updateState {
                it.copy(
                    cardData = CardData(
                        oweMoney = 2280f,
                        alreadyOwed = 1620f,
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
                updateState { it.copy(debts = debts, isLoading = false) }
            }.onFailure { err ->
                updateState { it.copy(isLoading = false) }
                emitEffect(EventDetailsEffect.ShowError(err.message ?: "Ошибка загрузки балансов"))
            }
        }
    }

    private fun loadOptimizedDebts(eventId: Int) {
        updateState { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            detailsRepository.optimizeDebts(eventId)
            val result = detailsRepository.getOptimizedDebts(eventId)
            result.onSuccess { debts ->
                var myOwe = 0f
                var oweToMe = 0f
                debts.forEach {
                    if (it.isPositive) oweToMe += it.amount
                    else myOwe += it.amount
                }
                updateState {
                    it.copy(
                        optimizedDebts = debts,
                        myOweSum = myOwe,
                        oweToMeSum = oweToMe,
                        isLoading = false
                    )
                }
            }.onFailure { err ->
                updateState { it.copy(isLoading = false) }
                emitEffect(
                    EventDetailsEffect.ShowError(
                        err.message ?: "Ошибка загрузки оптимизированных долгов"
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
}