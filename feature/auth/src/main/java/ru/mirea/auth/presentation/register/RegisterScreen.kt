package ru.mirea.auth.presentation.register

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.mirea.core.presentation.AppScaffold
import ru.mirea.core.util.collectInLaunchedEffect
import ru.mirea.core.util.useBy
import ru.mirea.uikit.R
import ru.mirea.uikit.components.buttons.FilledButton
import ru.mirea.uikit.components.buttons.OutlinedButton
import ru.mirea.uikit.components.divider.OrDivider
import ru.mirea.uikit.components.inputs.CommonEditTextField
import ru.mirea.uikit.components.inputs.PasswordEditTextField
import ru.mirea.uikit.theme.FinFlowTheme

@Composable
fun RegisterScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToMain: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val (state, event, effect) = useBy(viewModel)

    effect.collectInLaunchedEffect { registerEffect ->
        when (registerEffect) {
            is RegisterEffect.NavigateToMain -> onNavigateToMain()
            is RegisterEffect.ShowError -> {
                // Можно показать Snackbar или Toast
            }
        }
    }

    AppScaffold {
        RegisterContent(
            state = state,
            event = event,
            onNavigateToLogin = onNavigateToLogin
        )
    }
}

@Composable
private fun RegisterContent(
    state: RegisterState,
    event: (RegisterEvent) -> Unit,
    onNavigateToLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.register),
            style = FinFlowTheme.typography.headlineLarge,
            color = FinFlowTheme.colorScheme.text.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            modifier = Modifier.size(170.dp),
            painter = painterResource(R.drawable.logo),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        CommonEditTextField(
            value = state.username,
            onValueChange = { event(RegisterEvent.UsernameChanged(it)) },
            label = stringResource(R.string.username),
            hasError = state.username.isNotEmpty() && state.username.length < 3,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        CommonEditTextField(
            value = state.email,
            onValueChange = { event(RegisterEvent.EmailChanged(it)) },
            label = stringResource(R.string.email),
            hasError = state.email.isNotEmpty() && !state.isEmailValid,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        PasswordEditTextField(
            value = state.password,
            onValueChange = { event(RegisterEvent.PasswordChanged(it)) },
            label = stringResource(R.string.password),
            hasError = state.email.isNotEmpty() && state.password.length < 6,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        FilledButton(
            stringResource(R.string.register),
            onClick = { event(RegisterEvent.Submit) },
            enabled = state.buttonEnabled
        )

        Spacer(modifier = Modifier.height(16.dp))

        OrDivider()

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            label = stringResource(R.string.login_button),
            onClick = { onNavigateToLogin() },
        )

        if (state.error != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = state.error,
                color = FinFlowTheme.colorScheme.status.error
            )
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
    showBackground = true
)
@Composable
private fun LoginContentPreviewLight() {
    FinFlowTheme {
        RegisterContent(
            state = RegisterState(),
            event = {},
            onNavigateToLogin = {},
        )
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
    backgroundColor = 0xFF101010, showBackground = true
)
@Composable
private fun LoginContentPreviewDark() {
    FinFlowTheme {
        RegisterContent(
            state = RegisterState(),
            event = {},
            onNavigateToLogin = {},
        )
    }
}