package ru.mirea.uikit.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf


object FinFlowTheme {
    /**
     * Retrieves the current [ColorScheme].
     */
    val colorScheme: FFColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalFFColorScheme.current

    /**
     * Retrieves the current [Shapes].
     */
    val shapes: FFShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalFFShapes.current

    /**
     * Retrieves the current [FFTypography].
     */
    val typography: FFTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalFFTypography.current
}

@Composable
fun FinFlowTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val materialColorScheme = when {
        darkTheme -> darkColorScheme()
        else -> lightColorScheme()
    }

    val colorScheme = when {
        darkTheme -> darkFFColorScheme
        else -> lightFFColorScheme
    }

    CompositionLocalProvider(
        LocalFFShapes provides ffShapes,
        LocalFFColorScheme provides colorScheme,
        LocalFFTypography provides ffType
    ) {
        MaterialTheme(
            colorScheme = colorScheme.toMaterialColorScheme(
                defaultColorScheme = materialColorScheme
            ),
            typography = Typography,
            content = content
        )
    }

}


val LocalFFColorScheme: ProvidableCompositionLocal<FFColorScheme> =
    compositionLocalOf { lightFFColorScheme }

val LocalFFShapes: ProvidableCompositionLocal<FFShapes> =
    compositionLocalOf { ffShapes }

val LocalFFTypography: ProvidableCompositionLocal<FFTypography> =
    compositionLocalOf { ffType }
