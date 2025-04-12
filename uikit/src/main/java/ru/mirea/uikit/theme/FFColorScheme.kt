package ru.mirea.uikit.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color


val lightFFColorScheme: FFColorScheme = FFColorScheme(
    text = FFColorScheme.TextColors(
        primary = LightColors.Black,
        secondary = LightColors.LightGray,
        negative = LightColors.LightYellow,
        positive = LightColors.LightGreen,
        brand = LightColors.GreenBrand,
        invert = LightColors.White
    ),
    background = FFColorScheme.BackgroundColors(
        brand = LightColors.GreenBrand,
        primary = LightColors.White,
        secondary = LightColors.Gray,
        tertiary = LightColors.StrongGray,
        inverted = DarkColors.Black
    ),
    border = FFColorScheme.BorderColors(
        action = LightColors.Orange,
        brand = LightColors.GreenBrand,
        outline = LightColors.LightGray,
    ),
    icon = FFColorScheme.IconColors(
        primary = LightColors.White,
        secondary = LightColors.LightIconGray
    ),
    status = FFColorScheme.StatusColors(
        error = LightColors.Red,
        warning = LightColors.Yellow,
        success = LightColors.Green
    )
)

val darkFFColorScheme: FFColorScheme = FFColorScheme(
    text = FFColorScheme.TextColors(
        primary = DarkColors.White,
        secondary = DarkColors.LightGray,
        negative = DarkColors.LightYellow,
        positive = DarkColors.LightGreen,
        brand = DarkColors.GreenBrand,
        invert = DarkColors.Black
    ),
    background = FFColorScheme.BackgroundColors(
        brand = DarkColors.GreenBrand,
        primary = DarkColors.Black,
        secondary = DarkColors.Gray,
        tertiary = DarkColors.StrongGray,
        inverted = DarkColors.White
    ),
    border = FFColorScheme.BorderColors(
        action = DarkColors.Orange,
        brand = DarkColors.GreenBrand,
        outline = DarkColors.GreenBrand,
    ),
    icon = FFColorScheme.IconColors(
        primary = DarkColors.White,
        secondary = DarkColors.LightIconGray
    ),
    status = FFColorScheme.StatusColors(
        error = DarkColors.Red,
        warning = DarkColors.Yellow,
        success = DarkColors.Green
    )
)

fun FFColorScheme.toMaterialColorScheme(
    defaultColorScheme: ColorScheme,
): ColorScheme = defaultColorScheme.copy(
    primary = text.brand,
    onPrimary = text.invert,
    secondary = background.secondary,
    onSecondary = text.primary,
    background = background.primary,
    onBackground = text.primary,
    error = status.error,
    outline = border.brand,
    surface = background.secondary,
    onSurface = text.primary
)

@Immutable
data class FFColorScheme(
    val text: TextColors,
    val background: BackgroundColors,
    val border: BorderColors,
    val icon: IconColors,
    val status: StatusColors,
) {
    /**
     * Defines all the text colors for the app.
     */
    @Immutable
    data class TextColors(
        val primary: Color,
        val secondary: Color,
        val negative: Color,
        val positive: Color,
        val brand: Color,
        val invert: Color,
    )

    @Immutable
    data class BackgroundColors(
        val brand: Color,
        val primary: Color,
        val secondary: Color,
        val tertiary: Color,
        val inverted: Color,
    )

    @Immutable
    data class IconColors(
        val primary: Color,
        val secondary: Color,
    )

    @Immutable
    data class StatusColors(
        val error: Color,
        val warning: Color,
        val success: Color
    )

    @Immutable
    data class BorderColors(
        val action: Color,
        val brand: Color,
        val outline: Color
    )
}