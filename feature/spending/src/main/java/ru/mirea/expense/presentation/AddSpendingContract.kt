package ru.mirea.expense.presentation

import android.net.Uri
import ru.mirea.expense.domain.model.EventUser

// State для экрана добавления траты
data class AddSpendingState(
    val name: String = "",
    val amount: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null,
    val payer: EventUser? = null,
    val splitType: SplitType = SplitType.EQUALLY,
    val participants: List<EventUser> = emptyList(),
    val participantShares: Map<Int, Int> = emptyMap(),
    val totalDistributed: Int = 0,
    val totalDistributedPercent: Int = 0,
    val buttonEnabled: Boolean = false,
    val eventId: Int? = null,
    val photoUri: String? = null,
)

// Event для экрана добавления траты
sealed interface AddSpendingEvent {
    data class NameChanged(val name: String) : AddSpendingEvent
    data class AmountChanged(val amount: Int) : AddSpendingEvent
    data object SaveClicked : AddSpendingEvent

    data object SelectPayerClicked : AddSpendingEvent
    data class PayerSelected(val user: EventUser) : AddSpendingEvent

    data object SelectSplitTypeClicked : AddSpendingEvent
    data class SplitTypeSelected(val splitType: SplitType) : AddSpendingEvent

    data object AddParticipantClicked : AddSpendingEvent
    data class ParticipantsSelected(val users: List<EventUser>) : AddSpendingEvent

    data class ParticipantShareChanged(val user: EventUser, val value: Int) : AddSpendingEvent
    data class SetEventId(val id: Int) : AddSpendingEvent
    data class PhotoSelected(val uri: Uri) : AddSpendingEvent
    data class QrScanned(val qr: String) : AddSpendingEvent
    data object RemovePhoto : AddSpendingEvent
}

// Effect для экрана добавления траты
sealed interface AddSpendingEffect {
    data object Saved : AddSpendingEffect
    data class ShowError(val message: String) : AddSpendingEffect
    data object ShowPayerBottomSheet : AddSpendingEffect
    data object ShowSplitTypeBottomSheet : AddSpendingEffect
    data object ShowParticipantsBottomSheet : AddSpendingEffect
}

enum class SplitType(val title: String) {
    EQUALLY(title = "Поровну"),
    UNEQUALLY(title = "Не равными частями"),
    PERCENTAGE(title = "В процентах"),
    SHARES(title = "В долях")
} 