package ru.mirea.uikit.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.dp

/**
 * Defines the shape scheme for the app.
 */
@Immutable
data class FFShapes(
    val none: RoundedCornerShape,
    val extraSmall: RoundedCornerShape,
    val small: RoundedCornerShape,
    val medium: RoundedCornerShape,
    val large: RoundedCornerShape,
    val extraLarge: RoundedCornerShape,
    val extraLargeTopRounded: RoundedCornerShape,
    val full: RoundedCornerShape,
)

val ffShapes: FFShapes = FFShapes(
    none = RoundedCornerShape(0.dp),
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(24.dp),
    extraLargeTopRounded = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
    full = RoundedCornerShape(percent = 50)
)