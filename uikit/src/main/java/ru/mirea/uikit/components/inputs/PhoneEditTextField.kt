package ru.lab4u.android.core.uikit.components.inputs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.mirea.uikit.components.inputs.BaseOutlinedEditTextField
import ru.mirea.uikit.theme.FinFlowTheme
import ru.mirea.uikit.utils.MaskVisualTransformation

@Composable
fun PhoneEditTextField(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit = {},
    editable: Boolean = true,
    hasError: Boolean = false,
    errorText: String? = null,
) {
    BaseOutlinedEditTextField(
        value = value,
        labelText = label,
        onValueChange = onValueChange,
        enabled = editable,
        isError = hasError,
        visualTransformation = MaskVisualTransformation(PHONE_MASK),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        supportingText = if (!errorText.isNullOrBlank()) {
            @Composable {
                Text(
                    text = errorText,
                    style = FinFlowTheme.typography.labelLarge,
                    color = FinFlowTheme.colorScheme.status.error
                )
            }
        } else null,
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp),

        )
}

const val PHONE_MASK = "+7 (###) ###-##-##"

@Preview(showBackground = true)
@Composable
private fun PhoneEditTextField_preview() {
    FinFlowTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            PhoneEditTextField(
                label = "Мобильный телефон",
                value = "",
                editable = false,
                onValueChange = {},
            )

            PhoneEditTextField(
                label = "Мобильный телефон",
                value = "3453545454",
                onValueChange = {},
            )

            PhoneEditTextField(
                label = "Мобильный телефон",
                value = "762394",
                onValueChange = {},
                hasError = true,
                errorText = "Неверно введен телефон"
            )
        }

    }
}