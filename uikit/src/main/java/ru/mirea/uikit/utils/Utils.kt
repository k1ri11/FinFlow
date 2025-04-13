package ru.mirea.uikit.utils

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
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

@Composable
fun SystemNavigationPaddings() {
    Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.systemBars))
}

/** Last item in the list apply paddings for the bottom navigation */
fun LazyListScope.systemNavigationPaddings() {
    item { Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.systemBars)) }
}