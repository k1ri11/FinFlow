package ru.mirea.uikit.utils

import androidx.compose.ui.Modifier

/**
 * Conditional modifier "if true - else"
 */
fun Modifier.conditional(condition: Boolean, modifier: Modifier.() -> Modifier): Modifier {
    return if (condition) {
        then(modifier(Modifier))
    } else {
        this
    }
}