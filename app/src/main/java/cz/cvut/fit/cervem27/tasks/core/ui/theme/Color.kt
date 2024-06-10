package cz.cvut.fit.cervem27.tasks.core.ui.theme

import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val LightColorSchema = lightColorScheme(
    background = Color(0xFFDAD7D7),
    onBackground = Color(0xFF000000),
    surface = Color(0xFFDAD7D7),
    onSurface = Color(0xFF000000),
    primary = Color(0xFF3F51B5),
    secondary = Color(0xFF4C64DF),
    tertiary = Color(0xFF505050),
    primaryContainer = Color(0xFFF4F4F9),
    onPrimaryContainer = Color(0xFF000000),
    secondaryContainer = Color(0xFFFFFFFF),
    onSecondaryContainer = Color(0xFF000000),
    tertiaryContainer = Color(0xFF60BB4B),
    onPrimary = Color(0xFFFFFFFF),
    onSecondary = Color(0xFFFFFFFF),
    onTertiary = Color(0xFFFFFFFF),
    onTertiaryContainer = Color(0xFF086800),
    error = Color(0xFFCC3737),
    onSurfaceVariant = Color(0xFF000000),
    outlineVariant = Color(0xFF000000),
)

val DarkColorSchema = lightColorScheme(
    background = Color(0xFF1A1A1A),
    onBackground = Color(0xFFFFFFFF),
    surface = Color(0xFF1A1A1A),
    onSurface = Color(0xFFFFFFFF),
    primary = Color(0xFF9595FE),
    secondary = Color(0xFF6E6EC0),
    tertiary = Color(0xFFB1A5A5),
    primaryContainer = Color(0xFF0A0A0A),
    onPrimaryContainer = Color(0xffffffff),
    secondaryContainer = Color(0xFF3A3939),
    onSecondaryContainer = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFF71B974),
    onPrimary = Color(0xFF181819),
    onSecondary = Color(0xFF181819),
    onTertiary = Color(0xFF181819),
    onTertiaryContainer = Color(0xFF1F5C21),
    error = Color(0xFFC47E7E),
    onSurfaceVariant = Color(0xFFFFFFFF),
    outlineVariant = Color(0xFFFFFFFF),

)

// color picker for category icon is based on hue selection
// IconColorsConstants define saturation and lightness
object IconColorsConstants {
    private const val DEFAULT_SATURATION = 0.5f
    private const val DEFAULT_LIGHTNESS = 0.5f

    fun hslColor(hue: Float): Color {
        return Color.hsl(
            hue = hue,
            saturation = DEFAULT_SATURATION,
            lightness = DEFAULT_LIGHTNESS
        )
    }
}