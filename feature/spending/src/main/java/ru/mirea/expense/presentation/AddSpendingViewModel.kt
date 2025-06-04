package ru.mirea.expense.presentation

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore.Images.Media.getBitmap
import androidx.lifecycle.viewModelScope
import com.google.zxing.BinaryBitmap
import com.google.zxing.MultiFormatReader
import com.google.zxing.RGBLuminanceSource
import com.google.zxing.common.HybridBinarizer
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import okhttp3.internal.toImmutableMap
import ru.mirea.core.util.BaseViewModel
import ru.mirea.expense.data.models.TransactionRequestDto
import ru.mirea.expense.domain.repository.ExpenseRepository
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class AddSpendingViewModel @Inject constructor(
    private val repository: ExpenseRepository,
    @ApplicationContext private val context: Context,
) : BaseViewModel<AddSpendingState, AddSpendingEvent, AddSpendingEffect>(AddSpendingState()) {

    override fun event(event: AddSpendingEvent) {
        when (event) {
            is AddSpendingEvent.NameChanged -> {
                updateState { it.copy(name = event.name) }
                updateDistribution()
            }

            is AddSpendingEvent.AmountChanged -> {
                updateState { it.copy(amount = event.amount) }
                updateDistribution()
            }

            is AddSpendingEvent.SaveClicked -> {
                saveSpending()
            }

            is AddSpendingEvent.SelectPayerClicked -> {
                emitEffect(AddSpendingEffect.ShowPayerBottomSheet)
            }

            is AddSpendingEvent.PayerSelected -> {
                updateState { it.copy(payer = event.user) }
            }

            is AddSpendingEvent.SelectSplitTypeClicked -> {
                emitEffect(AddSpendingEffect.ShowSplitTypeBottomSheet)
            }

            is AddSpendingEvent.SplitTypeSelected -> {
                updateState { it.copy(splitType = event.splitType) }
                updateDistribution()
            }

            is AddSpendingEvent.AddParticipantClicked -> {
                emitEffect(AddSpendingEffect.ShowParticipantsBottomSheet)
            }

            is AddSpendingEvent.ParticipantsSelected -> {
                val oldShares = state.value.participantShares
                val newShares = event.users.associate { user ->
                    user.id to (oldShares[user.id] ?: 0f)
                }
                updateState {
                    it.copy(
                        participants = event.users,
                        participantShares = newShares.toImmutableMap()
                    )
                }
                updateDistribution()
            }

            is AddSpendingEvent.ParticipantShareChanged -> {
                val updatedShares = state.value.participantShares.toMutableMap()
                updatedShares[event.user.id] = event.value
                updateState { it.copy(participantShares = updatedShares.toImmutableMap()) }
                updateDistribution()
            }

            is AddSpendingEvent.SetEventId -> {
                updateState { it.copy(eventId = event.id) }
            }

            is AddSpendingEvent.PhotoSelected -> {
                handleSelectedPhoto(event.uri)
            }

            is AddSpendingEvent.QrScanned -> {
                val sum = parseSumFromQr(event.qr)
                updateState { it.copy(amount = sum ?: it.amount) }
                updateDistribution()
            }

            AddSpendingEvent.RemovePhoto -> updateState { it.copy(photoUri = "") }
        }
        updateState { it.copy(buttonEnabled = isFormValid()) }
    }

    private fun handleSelectedPhoto(uri: Uri) {
        updateState { it.copy(photoUri = uri.toString()) }
        scanQrFromUri(context, uri) { qr ->
            qr?.let {
                val sum = parseSumFromQr(qr)
                updateState { it.copy(amount = sum ?: it.amount) }
            }
        }
    }

    private fun scanQrFromUri(context: Context, imageUri: Uri, onResult: (String?) -> Unit) {
        try {
            val bitmap = getBitmapFromUri(context, imageUri) ?: return onResult(null)
            val result = decodeQrCode(bitmap)
            onResult(result)
        } catch (e: Exception) {
            onResult(null)
        }
    }

    private fun getBitmapFromUri(context: Context, uri: Uri): Bitmap? {
        return try {
            getBitmap(context.contentResolver, uri)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private fun decodeQrCode(bitmap: Bitmap): String? {
        val intArray = IntArray(bitmap.width * bitmap.height)
        bitmap.getPixels(intArray, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        val luminanceSource = RGBLuminanceSource(bitmap.width, bitmap.height, intArray)
        val binaryBitmap = BinaryBitmap(HybridBinarizer(luminanceSource))
        return try {
            MultiFormatReader().decode(binaryBitmap).text
        } catch (e: Exception) {
            null
        }
    }

    private fun parseSumFromQr(qr: String): Int? {
        val regex = Regex("s=([0-9]+\\.[0-9]+)")
        val match = regex.find(qr)
        val value = match?.groupValues?.getOrNull(1)
        return value?.toDoubleOrNull()?.toInt()
    }

    private fun updateDistribution() {
        val s = state.value
        val amount = s.amount
        val shares = s.participantShares.values
        when (s.splitType) {
            SplitType.UNEQUALLY -> {
                val left = amount.toFloat() - shares.sum()
                updateState { it.copy(totalDistributed = left, totalDistributedPercent = 0f) }
            }

            SplitType.PERCENTAGE -> {
                val left = 100 - shares.sum()
                updateState { it.copy(totalDistributed = 0f, totalDistributedPercent = left) }
            }

            else -> {
                updateState { it.copy(totalDistributed = 0f, totalDistributedPercent = 0f) }
            }
        }
    }

    private fun isFormValid(): Boolean {
        val state = state.value
        return state.name.isNotBlank()
                && state.amount > 0
                && state.payer != null
                && state.participants.isNotEmpty()
                && when (state.splitType) {
            SplitType.EQUALLY -> state.participants.isNotEmpty()
            SplitType.UNEQUALLY -> allParticipantsFilled() && state.totalDistributed == 0f
            SplitType.PERCENTAGE -> allParticipantsFilled() && state.totalDistributedPercent == 0f
            SplitType.SHARES -> allParticipantsFilled()
        }
    }

    private fun allParticipantsFilled(): Boolean {
        return state.value.participants.all { (state.value.participantShares[it.id] ?: 0f) > 0f }
    }

    private fun saveSpending() {
        val currentState = state.value
        val eventId = currentState.eventId ?: return
        val type = when (currentState.splitType) {
            SplitType.EQUALLY, SplitType.UNEQUALLY -> "amount"
            SplitType.PERCENTAGE -> "percent"
            SplitType.SHARES -> "units"
        }
        val portion = when (currentState.splitType) {
            SplitType.EQUALLY -> currentState.participants.associate { it.id to (currentState.amount.toFloat() / currentState.participants.size.toFloat()) }
            else -> currentState.participantShares
        }
        val requestBody = TransactionRequestDto(
            type = type,
            fromUser = currentState.payer?.id ?: 0,
            amount = currentState.amount.toDouble(),
            name = currentState.name,
            portion = portion,
            users = currentState.participants.map { it.id }
        )
        updateState { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = repository.saveTransaction(eventId, requestBody)
            updateState { it.copy(isLoading = false) }
            result.onSuccess {
                emitEffect(AddSpendingEffect.Saved)
            }.onFailure { err ->
                emitEffect(AddSpendingEffect.ShowError(err.message ?: "Ошибка сохранения траты"))
            }
        }
    }
} 