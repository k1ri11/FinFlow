package ru.mirea.expense.presentation

import android.content.Context
import android.content.res.Configuration
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.MutableSharedFlow
import ru.mirea.core.navigation.navigator.Navigator
import ru.mirea.core.util.UiHandler
import ru.mirea.core.util.collectInLaunchedEffect
import ru.mirea.core.util.useBy
import ru.mirea.expense.domain.model.EventUser
import ru.mirea.expense.domain.model.UserProfile
import ru.mirea.expense.presentation.AddSpendingEvent.AddParticipantClicked
import ru.mirea.expense.presentation.AddSpendingEvent.AmountChanged
import ru.mirea.expense.presentation.AddSpendingEvent.NameChanged
import ru.mirea.expense.presentation.AddSpendingEvent.ParticipantShareChanged
import ru.mirea.expense.presentation.AddSpendingEvent.PayerSelected
import ru.mirea.expense.presentation.AddSpendingEvent.PhotoSelected
import ru.mirea.expense.presentation.AddSpendingEvent.SaveClicked
import ru.mirea.expense.presentation.AddSpendingEvent.SelectPayerClicked
import ru.mirea.expense.presentation.AddSpendingEvent.SelectSplitTypeClicked
import ru.mirea.expense.presentation.AddSpendingEvent.SplitTypeSelected
import ru.mirea.expense.presentation.bottom_sheets.SplitTypeBS
import ru.mirea.expense.presentation.bottom_sheets.select_friend.ParticipantsBS
import ru.mirea.expense.presentation.bottom_sheets.select_friend.SelectPayerBS
import ru.mirea.uikit.AppScaffold
import ru.mirea.uikit.R
import ru.mirea.uikit.components.buttons.FilledButton
import ru.mirea.uikit.components.buttons.SmallOutlinedButton
import ru.mirea.uikit.components.inputs.CommonEditTextField
import ru.mirea.uikit.components.inputs.SumTextField
import ru.mirea.uikit.components.top_bar.CommonTopBar
import ru.mirea.uikit.theme.FinFlowTheme
import ru.mirea.uikit.utils.SystemNavigationPaddings
import java.io.File

@Composable
fun AddSpendingScreen(
    eventId: Int,
    holder: UiHandler<AddSpendingState, AddSpendingEvent, AddSpendingEffect>,
    navigateBack: () -> Unit,
) {
    val (state, event, effect) = holder
    val context = LocalContext.current
    var showPayerBS by remember { mutableStateOf(false) }
    var showSplitTypeBS by remember { mutableStateOf(false) }
    var showParticipantsBS by remember { mutableStateOf(false) }
    var showPhotoDialog by remember { mutableStateOf(false) }
    var tempImageUri by remember { mutableStateOf<Uri?>(null) }

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let {
                event(PhotoSelected(it))

            }
        }
    )

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success && tempImageUri != null) {
                event(PhotoSelected(tempImageUri!!))
            }
        }
    )

    LaunchedEffect(eventId) {
        event(AddSpendingEvent.SetEventId(eventId))
    }

    effect.collectInLaunchedEffect { spendingEffect ->
        when (spendingEffect) {
            is AddSpendingEffect.Saved -> navigateBack()
            is AddSpendingEffect.ShowError -> {
                Toast.makeText(context, spendingEffect.message, Toast.LENGTH_SHORT).show()
            }

            is AddSpendingEffect.ShowPayerBottomSheet -> showPayerBS = true
            is AddSpendingEffect.ShowSplitTypeBottomSheet -> showSplitTypeBS = true
            is AddSpendingEffect.ShowParticipantsBottomSheet -> showParticipantsBS = true
        }
    }

    if (showPhotoDialog) {
        Dialog(onDismissRequest = { showPhotoDialog = false }) {
            DialogContent(
                state = state,
                onGalleryButtonClick = {
                    showPhotoDialog = false
                    singlePhotoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
                onCameraButtonClick = {
                    showPhotoDialog = false
                    tempImageUri = createImageUri(context)
                    cameraLauncher.launch(tempImageUri!!)
                },
                onDeleteButtonClick = {
                    showPhotoDialog = false
                    event(AddSpendingEvent.RemovePhoto)
                },
            )
        }
    }

    AppScaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CommonTopBar(
                modifier = Modifier.padding(horizontal = 16.dp),
                title = stringResource(R.string.spending_name),
                leftIconId = R.drawable.ic_arrow_back,
                onLeftIconClick = navigateBack
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .clip(FinFlowTheme.shapes.extraLargeTopRounded)
                    .background(FinFlowTheme.colorScheme.background.tertiary)
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (state.splitType == SplitType.UNEQUALLY) {
                    when {
                        state.totalDistributed == 0 -> Text(
                            text = stringResource(R.string.all_distributed),
                            color = FinFlowTheme.colorScheme.status.success,
                            style = FinFlowTheme.typography.bodyMedium
                        )

                        state.totalDistributed < 0 -> Text(
                            text = stringResource(R.string.wrong_distribution),
                            color = FinFlowTheme.colorScheme.status.error,
                            style = FinFlowTheme.typography.bodyMedium
                        )

                        else -> Text(
                            text = stringResource(R.string.distribute_left, state.totalDistributed),
                            color = FinFlowTheme.colorScheme.text.primary,
                            style = FinFlowTheme.typography.bodyMedium
                        )
                    }
                }
                if (state.splitType == SplitType.PERCENTAGE) {
                    when {
                        state.totalDistributedPercent == 0 -> Text(
                            text = stringResource(R.string.all_distributed),
                            color = FinFlowTheme.colorScheme.status.success,
                            style = FinFlowTheme.typography.bodyMedium
                        )

                        state.totalDistributedPercent < 0 -> Text(
                            text = stringResource(R.string.wrong_distribution),
                            color = FinFlowTheme.colorScheme.status.error,
                            style = FinFlowTheme.typography.bodyMedium
                        )

                        else -> Text(
                            text = stringResource(
                                R.string.distribute_left_percent,
                                state.totalDistributedPercent
                            ),
                            color = FinFlowTheme.colorScheme.text.primary,
                            style = FinFlowTheme.typography.bodyMedium
                        )
                    }
                }
                FilledButton(
                    label = stringResource(R.string.add),
                    onClick = { event(SaveClicked) },
                    enabled = state.buttonEnabled
                )
                SystemNavigationPaddings()
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Photo(photoUrl = state.photoUri, onClick = { showPhotoDialog = true })
            SpendingFields(state, event)
            PayerAndSplitType(state, event)
            ParticipantsRow(event)
            ParticipantsList(state, event)

            state.error?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = it, color = MaterialTheme.colorScheme.error)
            }
        }
        if (showPayerBS) {
            SelectPayerBS(
                eventId = eventId,
                onDismiss = { showPayerBS = false },
                onUserSelected = { event(PayerSelected(it)) }
            )
        }
        if (showSplitTypeBS) {
            SplitTypeBS(
                onDismiss = { showSplitTypeBS = false },
                onSplitTypeSelected = { event(SplitTypeSelected(it)) }
            )
        }
        if (showParticipantsBS) {
            ParticipantsBS(
                eventId = eventId,
                selectedUsers = state.participants,
                onDismiss = { showParticipantsBS = false },
                onSave = { event(AddSpendingEvent.ParticipantsSelected(it)) }
            )
        }
    }
}

@Composable
private fun DialogContent(
    state: AddSpendingState,
    onGalleryButtonClick: () -> Unit,
    onCameraButtonClick: () -> Unit,
    onDeleteButtonClick: () -> Unit,
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                FinFlowTheme.colorScheme.background.primary,
                FinFlowTheme.shapes.large
            )
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(R.string.choose_photo_source),
                style = FinFlowTheme.typography.titleMedium,
                color = FinFlowTheme.colorScheme.text.primary,
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(R.string.choose_photo_hint),
                style = FinFlowTheme.typography.bodyMedium,
                color = FinFlowTheme.colorScheme.text.primary,
                textAlign = TextAlign.Center
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = onGalleryButtonClick) { Text(text = stringResource(R.string.gallery)) }
                Button(onClick = onCameraButtonClick) { Text(text = stringResource(R.string.camera)) }
            }
            if (state.photoUri != null) {
                Button(
                    onClick = onDeleteButtonClick,
                    colors = buttonColors(containerColor = FinFlowTheme.colorScheme.status.error)
                ) {
                    Text(
                        text = stringResource(R.string.delete_photo),
                        color = FinFlowTheme.colorScheme.text.invert
                    )
                }
            }
        }
    }
}

@Composable
private fun Photo(photoUrl: String? = null, onClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .size(120.dp)
            .clip(FinFlowTheme.shapes.medium)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = photoUrl,
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.add_photo),
            error = painterResource(R.drawable.add_photo)
        )
    }
}

@Composable
private fun SpendingFields(state: AddSpendingState, event: (AddSpendingEvent) -> Unit) {
    CommonEditTextField(
        value = state.name,
        onValueChange = { event(NameChanged(it)) },
        label = stringResource(R.string.spending_name),
        modifier = Modifier.fillMaxWidth()
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.spending_amount),
            style = FinFlowTheme.typography.titleMedium,
            color = FinFlowTheme.colorScheme.text.primary,
        )

        SumTextField(
            value = if (state.amount == 0) "" else state.amount.toString(),
            onValueChange = { str ->
                val intValue = str.toIntOrNull() ?: 0
                event(AmountChanged(intValue))
            },
            placeholder = stringResource(R.string.enter_amount)
        )

    }
}

@Composable
private fun PayerAndSplitType(state: AddSpendingState, event: (AddSpendingEvent) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = stringResource(R.string.paid_by),
                style = FinFlowTheme.typography.bodyMedium,
                color = FinFlowTheme.colorScheme.text.primary
            )
            SmallOutlinedButton(
                label = state.payer?.profile?.nickname ?: stringResource(R.string.select_payer),
                onClick = { event(SelectPayerClicked) },
                shape = FinFlowTheme.shapes.small,
                iconId = R.drawable.ic_drop_down
            )
        }
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = stringResource(R.string.split_type),
                style = FinFlowTheme.typography.bodyMedium,
                color = FinFlowTheme.colorScheme.text.primary
            )
            SmallOutlinedButton(
                modifier = Modifier.padding(4.dp),
                label = state.splitType.title,
                onClick = { event(SelectSplitTypeClicked) },
                shape = FinFlowTheme.shapes.small,
                iconId = R.drawable.ic_drop_down
            )
        }
    }
}

@Composable
private fun ParticipantsRow(event: (AddSpendingEvent) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(R.string.with_whom),
            style = FinFlowTheme.typography.bodyMedium,
            color = FinFlowTheme.colorScheme.text.primary,
        )
        SmallOutlinedButton(
            label = stringResource(R.string.add_participant),
            onClick = { event(AddParticipantClicked) },
            iconId = R.drawable.ic_plus,
            modifier = Modifier.background(FinFlowTheme.colorScheme.background.secondary)
        )
    }
}

@Composable
private fun ParticipantsList(state: AddSpendingState, event: (AddSpendingEvent) -> Unit) {
    val equallyValue =
        if (state.splitType == SplitType.EQUALLY && state.amount > 0 && state.participants.isNotEmpty()) {
            state.amount / state.participants.size
        } else 0

    state.participants.forEach { participant ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AsyncImage(
                model = participant.profile.photo,
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clip(FinFlowTheme.shapes.large),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.placeholder_money),
                error = painterResource(R.drawable.placeholder_money)
            )
            Text(
                modifier = Modifier.weight(1f),
                text = participant.profile.nickname,
                style = FinFlowTheme.typography.titleSmall,
                color = FinFlowTheme.colorScheme.text.primary
            )
            val value =
                if (state.splitType == SplitType.EQUALLY) equallyValue else (state.participantShares[participant.id]
                    ?: 0)
            TextField(
                modifier = Modifier
                    .width(100.dp),
                value = value.toString(),
                onValueChange = {
                    if (state.splitType != SplitType.EQUALLY) {
                        val intValue = it.toIntOrNull() ?: 0
                        event(ParticipantShareChanged(participant, intValue))
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                readOnly = state.splitType == SplitType.EQUALLY
            )
        }
    }
}

private fun createImageUri(context: Context): Uri {
    val file = File(context.cacheDir, "${System.currentTimeMillis()}.jpg")
    return FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
}

@Composable
fun AddSpendingNavScreen(navigator: Navigator, eventId: Int) {
    val viewModel: AddSpendingViewModel = hiltViewModel()
    val holder = useBy(viewModel)
    AddSpendingScreen(
        holder = holder,
        eventId = eventId,
        navigateBack = { navigator.popBackStack() }
    )
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun AddSpendingScreenPreview() {
    val fakeUser = EventUser(
        id = 1,
        name = "",
        isDummy = false,
        profile = UserProfile(
            userId = 1,
            nickname = "Вы",
            name = null,
            photo = null
        )
    )
    val fakeFriend1 = EventUser(
        id = 2,
        name = "",
        isDummy = false,
        profile = UserProfile(
            userId = 2,
            nickname = "Иван",
            name = null,
            photo = null
        )
    )
    val fakeFriend2 = EventUser(
        id = 3,
        name = "",
        isDummy = false,
        profile = UserProfile(
            userId = 3,
            nickname = "Оля",
            name = null,
            photo = null
        )
    )
    val fakeHolder = UiHandler<AddSpendingState, AddSpendingEvent, AddSpendingEffect>(
        state = AddSpendingState(
            name = "Обед",
            amount = 200,
            payer = fakeUser,
            splitType = SplitType.EQUALLY,
            participants = listOf(
                fakeFriend1,
                fakeFriend2
            ),
            totalDistributed = 200,
            totalDistributedPercent = 1444,
        ),
        dispatch = {},
        effectFlow = MutableSharedFlow()
    )
    FinFlowTheme {
        AddSpendingScreen(holder = fakeHolder, eventId = 1, navigateBack = {})
    }
}