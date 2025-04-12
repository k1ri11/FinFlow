package ru.mirea.uikit.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.mirea.uikit.R

val fontFamily = FontFamily(
    Font(R.font.roboto_light, FontWeight.Light),
    Font(R.font.roboto_regular, FontWeight.Normal),
    Font(R.font.roboto_medium, FontWeight.Medium),
    Font(R.font.roboto_bold, FontWeight.Bold),
)

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.5.sp,
    ),
    displayMedium = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 34.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.5.sp,
    ),
    displaySmall = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.5.sp,
    ),
    headlineLarge = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 30.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.5.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 28.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 26.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.5.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.5.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.5.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 20.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
    ),
)

/**
 * Defines all the text-styles for the app.
 */
@Immutable
data class FFTypography(
    /**
     * Text style for display large text.
     * @param fontSize 36.sp
     * @param lineHeight 28.sp
     */
    val displayLarge: TextStyle,
    /**
     * Text style for display medium text.
     * @param fontSize 34.sp
     * @param lineHeight 28.sp
     */
    val displayMedium: TextStyle,
    /**
     * Text style for display small text.
     * @param fontSize 32.sp
     * @param lineHeight 28.sp
     */
    val displaySmall: TextStyle,
    /**
     * Text style for headline large text.
     * @param fontSize 30.sp
     * @param lineHeight 28.sp
     */
    val headlineLarge: TextStyle,
    /**
     * Text style for headline medium text.
     * @param fontSize 28.sp
     * @param lineHeight 24.sp
     */
    val headlineMedium: TextStyle,
    /**
     * Text style for headline small text.
     * @param fontSize 26.sp
     * @param lineHeight 28.sp
     */
    val headlineSmall: TextStyle,
    /**
     * Text style for title large text.
     * @param fontSize 24.sp
     * @param lineHeight 28.sp
     */
    val titleLarge: TextStyle,
    /**
     * Text style for title medium text.
     * @param fontSize 22.sp
     * @param lineHeight 28.sp
     */
    val titleMedium: TextStyle,
    /**
     * Text style for title small text.
     * @param fontSize 20.sp
     * @param lineHeight 24.sp
     */
    val titleSmall: TextStyle,
    /**
     * Text style for body large text.
     * @param fontSize 18.sp
     * @param lineHeight 24.sp
     */
    val bodyLarge: TextStyle,
    /**
     * Text style for body medium text.
     * @param fontSize 16.sp
     * @param lineHeight 24.sp
     */
    val bodyMedium: TextStyle,
    /**
     * Text style for body small text.
     * @param fontSize 14.sp
     * @param lineHeight 20.sp
     */
    val bodySmall: TextStyle,
    /**
     * Text style for label large text.
     * @param fontSize 12.sp
     * @param lineHeight 16.sp
     */
    val labelLarge: TextStyle,
    /**
     * Text style for label medium text.
     * @param fontSize 12.sp
     * @param lineHeight 16.sp
     */
    val labelMedium: TextStyle,
    /**
     * Text style for label small text.
     * @param fontSize 10.sp
     * @param lineHeight 16.sp
     */
    val labelSmall: TextStyle,
)

/**
 * Derives a Material [Typography] from the [FFTypography].
 */
fun FFTypography.toMaterialTypography(): Typography = Typography(
    displayLarge = displayLarge,
    displayMedium = displayMedium,
    displaySmall = displaySmall,
    headlineLarge = headlineLarge,
    headlineMedium = headlineMedium,
    headlineSmall = headlineSmall,
    titleLarge = titleLarge,
    titleMedium = titleMedium,
    titleSmall = titleSmall,
    bodyLarge = bodyLarge,
    bodyMedium = bodyMedium,
    bodySmall = bodySmall,
    labelLarge = labelLarge,
    labelMedium = labelMedium,
    labelSmall = labelSmall,
)

val ffType: FFTypography = FFTypography(
    displayLarge = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.5.sp,
    ),
    displayMedium = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 34.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.5.sp,
    ),
    displaySmall = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.5.sp,
    ),
    headlineLarge = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 30.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.5.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 28.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 26.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.5.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.5.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.5.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 20.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.5.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
    ),
)

@Preview(showBackground = true)
@Composable
private fun AppTypography_preview() {
    FinFlowTheme {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = "Display medium (${Typography.displayMedium.fontSize})",
                style = Typography.displayMedium
            )
            Text(
                text = "Display small (${Typography.displaySmall.fontSize})",
                style = Typography.displaySmall
            )
            Text(
                text = "Headline large (${Typography.headlineLarge.fontSize})",
                style = Typography.headlineLarge
            )
            Text(
                text = "Headline medium (${Typography.headlineMedium.fontSize})",
                style = Typography.headlineMedium
            )
            Text(
                text = "Headline small (${Typography.headlineSmall.fontSize})",
                style = Typography.headlineSmall
            )
            Text(
                text = "Title large (${Typography.titleLarge.fontSize})",
                style = Typography.titleLarge
            )
            Text(
                text = "Title medium (${Typography.titleMedium.fontSize})",
                style = Typography.titleMedium
            )
            Text(
                text = "Title small (${Typography.titleSmall.fontSize})",
                style = Typography.titleSmall
            )
            Text(
                text = "Body large (${Typography.bodyLarge.fontSize})",
                style = Typography.bodyLarge
            )
            Text(
                text = "Body medium (${Typography.bodyMedium.fontSize})",
                style = Typography.bodyMedium
            )
            Text(
                text = "Body small (${Typography.bodySmall.fontSize})",
                style = Typography.bodySmall
            )
            Text(
                text = "Label large (${Typography.labelLarge.fontSize})",
                style = Typography.labelLarge
            )
            Text(
                text = "Label medium (${Typography.labelMedium.fontSize})",
                style = Typography.labelMedium
            )
            Text(
                text = "Label small (${Typography.labelSmall.fontSize})",
                style = Typography.labelSmall
            )
        }
    }
}

