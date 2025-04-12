package ru.mirea.uikit.components.inputs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.mirea.uikit.R
import ru.mirea.uikit.components.icon.FFIconButton
import ru.mirea.uikit.theme.FinFlowTheme

@Composable
fun PasswordEditTextField(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit = {},
    hasError: Boolean = false,
    errorText: String? = null,
) {
    var passwordVisibility by remember { mutableStateOf(false) }
    BaseOutlinedEditTextField(
        value = value,
        labelText = label,
        onValueChange = onValueChange,
        isError = hasError,
        supportingText = if (!errorText.isNullOrBlank()) {
            @Composable {
                Text(
                    text = errorText,
                    style = FinFlowTheme.typography.labelLarge,
                    color = FinFlowTheme.colorScheme.status.error
                )
            }
        } else null,
        trailingIcon = @Composable {
            FFIconButton(
                resId = when (passwordVisibility) {
                    false -> R.drawable.ic_eye
                    true -> R.drawable.ic_eye_off
                },
                onClick = { passwordVisibility = !passwordVisibility },
                tint = FinFlowTheme.colorScheme.icon.secondary
            )
        },

        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = when (passwordVisibility) {
            false -> PasswordVisualTransformation()
            true -> VisualTransformation.None
        },

        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
    )
}


@Preview(showBackground = true)
@Composable
private fun PasswordTextField_preview() {
    FinFlowTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            PasswordEditTextField(
                label = "Пароль",
                value = "",
                onValueChange = {},
            )

            PasswordEditTextField(
                label = "Пароль",
                value = "sk3434",
                onValueChange = {},
            )

            PasswordEditTextField(
                label = "Пароль",
                value = "sk3434",
                onValueChange = {},
                hasError = true,
                errorText = "Неверно введен пароль"
            )
        }

    }
}