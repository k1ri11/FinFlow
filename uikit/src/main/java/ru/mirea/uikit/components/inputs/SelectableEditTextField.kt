package ru.mirea.uikit.components.inputs

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.mirea.uikit.R
import ru.mirea.uikit.components.icon.FFIconButton
import ru.mirea.uikit.theme.FinFlowTheme

@Composable
fun SelectableEditTextField(
    label: String,
    value: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    @DrawableRes trailingIconId: Int = R.drawable.ic_calendar,
    @DrawableRes additionalIconId: Int? = null,
    onAdditionalIconCLick: () -> Unit = {},
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
        readOnly = true,
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
            //todo поправить что фокус с иконки раскрытия бш не приходит на edittext
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                additionalIconId?.let {
                    FFIconButton(
                        containerModifier = Modifier.offset(x = 8.dp),
                        resId = additionalIconId,
                        tint = if (editable) FinFlowTheme.colorScheme.icon.brand else FinFlowTheme.colorScheme.icon.secondary,
                        onClick = onAdditionalIconCLick
                    )
                }
                FFIconButton(
                    resId = trailingIconId,
                    tint = if (editable) FinFlowTheme.colorScheme.icon.brand else FinFlowTheme.colorScheme.icon.secondary,
                    onClick = onClick
                )
            }
        },
        interactionSource = remember { MutableInteractionSource() }
            .also { interactionSource ->
                LaunchedEffect(interactionSource) {
                    interactionSource.interactions.collect {
                        if (it is PressInteraction.Release) {
                            onClick.invoke()
                        }
                    }
                }
            },
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick.invoke() }
            .padding(top = 8.dp),
    )
}


@Preview(showBackground = true)
@Composable
private fun SelectableEditTextField_preview() {
    FinFlowTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            SelectableEditTextField(
                label = "Дата рождения",
                value = "",
                editable = false,
                onValueChange = {},
                onClick = {}
            )

            SelectableEditTextField(
                label = "Дата рождения",
                value = "23.07.2002",
                onValueChange = {},
                onClick = {}
            )

            SelectableEditTextField(
                label = "Дата рождения",
                value = "23.07.2002",
                onValueChange = {},
                trailingIconId = R.drawable.ic_calendar,
                additionalIconId = R.drawable.ic_eye,
                onClick = {}
            )

            SelectableEditTextField(
                label = "Дата рождения",
                value = "23.07.2002",
                onValueChange = {},
                hasError = true,
                errorText = "возраст слишком велик",
                onClick = {}
            )
        }

    }
}