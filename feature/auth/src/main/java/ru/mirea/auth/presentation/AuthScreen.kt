package ru.mirea.auth.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.mirea.auth.R
import ru.mirea.core.presentation.AppScaffold
import ru.mirea.core.util.collectInLaunchedEffect
import ru.mirea.core.util.useBy

@Composable
fun AuthScreen(
    onNavigateToMain: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val (state, event, effect) = useBy(viewModel)

    effect.collectInLaunchedEffect { authEffect ->
        when (authEffect) {
            is AuthEffect.NavigateToMain -> onNavigateToMain()
            is AuthEffect.ShowError -> {
                // Можно показать Snackbar или Toast
            }
        }
    }

    AppScaffold {
        AuthContent(
            state = state,
            event = event
        )
    }
}

@Composable
private fun AuthContent(
    state: AuthState,
    event: (AuthEvent) -> Unit,
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
            text = if (state.isLoginMode) "Вход" else "Регистрация",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = state.email,
            onValueChange = { event(AuthEvent.EmailChanged(it)) },
            label = { Text("Email") },
            isError = state.email.isNotEmpty() && !state.isEmailValid,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = state.password,
            onValueChange = { event(AuthEvent.PasswordChanged(it)) },
            label = { Text("Пароль") },
            visualTransformation = if (state.isPasswordVisible)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { event(AuthEvent.TogglePasswordVisibility) }) {
                    Icon(
                        painterResource(
                            id = if (state.isPasswordVisible) R.drawable.ic_eye_off else R.drawable.ic_eye
                        ),
                        contentDescription = if (state.isPasswordVisible) "Скрыть пароль" else "Показать пароль"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        if (!state.isLoginMode) {
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = state.name,
                onValueChange = { event(AuthEvent.NameChanged(it)) },
                label = { Text("Имя") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { event(AuthEvent.Submit) },
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.isLoading && state.isEmailValid &&
                    state.password.length >= 6 &&
                    (!state.isLoginMode && state.name.isNotBlank() || state.isLoginMode)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text(if (state.isLoginMode) "Войти" else "Зарегистрироваться")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { event(AuthEvent.ToggleMode) }) {
            Text(if (state.isLoginMode) "Создать аккаунт" else "Уже есть аккаунт?")
        }

        state.error?.let { error ->
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
} 