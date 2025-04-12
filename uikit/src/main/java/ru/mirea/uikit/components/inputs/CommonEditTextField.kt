package ru.mirea.uikit.components.inputs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.mirea.uikit.theme.FinFlowTheme

@Composable
fun CommonEditTextField(
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

@Preview(showBackground = true)
@Composable
private fun CommonAppEditText_preview() {
    FinFlowTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CommonEditTextField(
                label = "Электронная почта",
                value = "",
                editable = false,
                onValueChange = {},
            )

            CommonEditTextField(
                label = "Электронная почта",
                value = "aajnasd",
                onValueChange = {},
            )

            CommonEditTextField(
                label = "Электронная почта",
                value = "aajnasd",
                onValueChange = {},
                hasError = true,
                errorText = "Неверно введен email"
            )
        }

    }
}

