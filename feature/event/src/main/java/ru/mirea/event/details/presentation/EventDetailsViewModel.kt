package ru.mirea.event.details.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.mirea.core.util.BaseViewModel
import ru.mirea.event.details.data.repository.DetailsRepository
import ru.mirea.event.details.domain.toDomain
import ru.mirea.event.details.presentation.widgets.CardData
import javax.inject.Inject

@HiltViewModel
class EventDetailsViewModel @Inject constructor(
    private val detailsRepository: DetailsRepository,
) : BaseViewModel<EventDetailsState, EventDetailsEvent, EventDetailsEffect>(EventDetailsState()) {

    private val userId = 1 // захардкоженный id пользователя

    override fun event(event: EventDetailsEvent) {
        when (event) {
            is EventDetailsEvent.LoadDetails -> {
                updateState { it.copy(eventId = event.eventId) }
                loadDetails(event.eventId)
                loadDebts(event.eventId)
                loadOptimizedDebts(event.eventId)
                loadTransactions(event.eventId)
            }

            is EventDetailsEvent.TabSelected -> {
                updateState { it.copy(selectedTab = event.tab) }
            }

            is EventDetailsEvent.ShowOnlyMineChanged -> {
                updateState { it.copy(showOnlyMine = event.value) }
                filterDebts()
                filterOptimizedDebts()
            }
        }
    }

    private fun filterDebts() {
        val stateValue = state.value
        val filtered = if (stateValue.showOnlyMine) {
            stateValue.debts.filter { it.fromUserId == userId || it.toUserId == userId }
        } else stateValue.debts
        val oweToMeSum = filtered.filter { it.toUserId == userId }.sumOf { it.amount }
        updateState { it.copy(filteredDebts = filtered, oweToMeSum = oweToMeSum) }
    }

    private fun filterOptimizedDebts() {
        val stateValue = state.value
        val filtered = if (stateValue.showOnlyMine) {
            stateValue.optimizedDebts.filter { it.fromUserId == userId || it.toUserId == userId }
        } else stateValue.optimizedDebts
        val myOweSum = filtered.filter { it.fromUserId == userId }.sumOf { it.amount }
        updateState { it.copy(filteredOptimizedDebts = filtered, myOweSum = myOweSum) }
    }

    private fun loadDetails(eventId: Int) {
        updateState { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
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
                updateState { it.copy(debts = debts, isLoading = false) }
                filterDebts()
            }.onFailure { err ->
                updateState { it.copy(isLoading = false) }
                emitEffect(EventDetailsEffect.ShowError(err.message ?: "Ошибка загрузки балансов"))
            }
        }
    }

    private fun loadOptimizedDebts(eventId: Int) {
        updateState { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            val result = detailsRepository.getOptimizedDebts(eventId)
            result.onSuccess { debts ->
                updateState { it.copy(optimizedDebts = debts, isLoading = false) }
                filterOptimizedDebts()
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