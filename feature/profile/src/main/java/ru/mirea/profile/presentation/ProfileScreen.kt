package ru.mirea.profile.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import kotlinx.coroutines.flow.MutableSharedFlow
import ru.lab4u.android.core.uikit.components.inputs.PhoneEditTextField
import ru.mirea.core.util.UiHandler
import ru.mirea.core.util.collectInLaunchedEffect
import ru.mirea.core.util.useBy
import ru.mirea.profile.presentation.ProfileEvent.ToggleEditMode
import ru.mirea.uikit.AppScaffold
import ru.mirea.uikit.R
import ru.mirea.uikit.components.buttons.FilledButton
import ru.mirea.uikit.components.buttons.OutlinedButton
import ru.mirea.uikit.components.inputs.CommonEditTextField
import ru.mirea.uikit.components.inputs.SelectableEditTextField
import ru.mirea.uikit.components.loader.CircularLoader
import ru.mirea.uikit.components.top_bar.CommonTopBar
import ru.mirea.uikit.theme.Dimens
import ru.mirea.uikit.theme.FinFlowTheme
import ru.mirea.uikit.utils.SystemNavigationPaddings
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ProfileScreen(
    holder: UiHandler<ProfileState, ProfileEvent, ProfileEffect>,
) {
    val (state, event, effect) = holder

    effect.collectInLaunchedEffect { profileEffect ->
        when (profileEffect) {
            is ProfileEffect.ShowError -> {
                // TODO: Show error
            }

            ProfileEffect.ProfileUpdated -> {
                // TODO: Show success message
            }

            ProfileEffect.LoggedOut -> {}
        }
    }

    AppScaffold(
        topBar = {
            CommonTopBar(
                modifier = Modifier.padding(horizontal = 16.dp),
                title = stringResource(R.string.profile),
                rightIconId = R.drawable.ic_edit,
                onRightIconClick = { event.invoke(ToggleEditMode) }
            )
        }
    ) { paddingValues ->
        ProfileScreenContent(state, event, paddingValues)
    }
}

@Composable
private fun ProfileScreenContent(
    state: ProfileState,
    event: (ProfileEvent) -> Unit,
    paddingValues: PaddingValues,
) {
    var showDatePicker by remember { mutableStateOf(false) }

    if (state.isLoading) {
        CircularLoader()
        return
    }

    val profile = state.profile ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AsyncImage(
            model = profile.avatarUrl ?: "https://via.placeholder.com/100",
            contentDescription = "Аватар",
            modifier = Modifier.size(100.dp)
        )

        CommonEditTextField(
            value = profile.name ?: "",
            onValueChange = { event(ProfileEvent.NameChanged(it)) },
            label = stringResource(R.string.name),
            editable = state.isEditing,
        )

        CommonEditTextField(
            value = profile.nickname ?: "",
            onValueChange = { event(ProfileEvent.NicknameChanged(it)) },
            label = stringResource(R.string.nickname),
            editable = state.isEditing,
        )

        PhoneEditTextField(
            value = profile.phone ?: "",
            onValueChange = { event(ProfileEvent.PhoneChanged(it)) },
            label = stringResource(R.string.telephone),
            editable = state.isEditing,
        )

        CommonEditTextField(
            value = profile.email ?: "",
            onValueChange = { event(ProfileEvent.EmailChanged(it)) },
            label = stringResource(R.string.email),
            editable = state.isEditing,
            modifier = Modifier.fillMaxWidth()
        )

        //todo отдавать на бэк в timestamp
        SelectableEditTextField(
            value = profile.birthDate ?: "",
            onValueChange = { },
            label = stringResource(R.string.birth_date),
            editable = state.isEditing,
            modifier = Modifier.fillMaxWidth(),
            trailingIconId = R.drawable.ic_calendar,
            onClick = { showDatePicker = true }
        )

        Spacer(modifier = Modifier.weight(1f))

        if (state.isEditing) {
            FilledButton(
                label = stringResource(R.string.save),
                onClick = { event(ProfileEvent.Submit) },
            )
        }

        OutlinedButton(
            label = stringResource(R.string.logout),
            onClick = { event(ProfileEvent.Logout) }
        )

        Box {
            Spacer(Modifier.height(Dimens.BOTTOM_BAR_HEIGHT))
            SystemNavigationPaddings()
        }
    }

    if (showDatePicker) {
        ShowDatePicker(
            onDateSelected = { date ->
                event(ProfileEvent.BirthDateChanged(date))
            },
            onDismiss = { showDatePicker = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ShowDatePicker(
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    val datePickerState = rememberDatePickerState()
    val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                datePickerState.selectedDateMillis?.let { millis ->

                    val formattedDate = formatter.format(Date(millis))
                    onDateSelected(formattedDate)
                }
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Отмена")
            }
        }
    ) {
        DatePicker(
            state = datePickerState
        )
    }
}

@Composable
fun ProfileNavScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val holder = useBy(viewModel)
    ProfileScreen(holder = holder)
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
    showBackground = true
)
@Composable
private fun ProfileScreenContentPreviewLight() {
    FinFlowTheme {
        ProfileScreen(
            UiHandler(
                state = ProfileState(isEditing = true),
                dispatch = {},
                effectFlow = MutableSharedFlow()
            )
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun ProfileScreenContentPreviewDark() {
    FinFlowTheme {
        ProfileScreen(
            UiHandler(
                state = ProfileState(isEditing = true),
                dispatch = {},
                effectFlow = MutableSharedFlow()
            )
        )
    }
}