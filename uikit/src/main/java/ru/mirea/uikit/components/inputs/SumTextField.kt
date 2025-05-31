package ru.mirea.uikit.components.inputs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType.Companion.Number
import androidx.compose.ui.text.style.TextAlign
import ru.mirea.uikit.theme.FinFlowTheme

@Composable
fun SumTextField(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
) {
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(keyboardType = Number),
        textStyle = FinFlowTheme.typography.headlineMedium.copy(
            textAlign = TextAlign.Center,
            color = FinFlowTheme.colorScheme.text.primary
        ),
        placeholder = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = placeholder,
                style = FinFlowTheme.typography.headlineMedium.copy(
                    textAlign = TextAlign.Center,
                    color = FinFlowTheme.colorScheme.text.primary
                )
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
        ),

        )
}