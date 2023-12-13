package com.example.pantrypal.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColors = lightColorScheme(
    primary = pp_theme_list_primary,
    onPrimary = pp_theme_list_onPrimary,
    primaryContainer = pp_theme_list_primaryContainer,
    onPrimaryContainer = pp_theme_list_onPrimaryContainer,
    secondary = pp_theme_list_secondary,
    onSecondary = pp_theme_list_onSecondary,
    secondaryContainer = pp_theme_list_secondaryContainer,
    onSecondaryContainer = pp_theme_list_onSecondaryContainer,
    tertiary = pp_theme_list_tertiary,
    onTertiary = pp_theme_list_onTertiary,
    tertiaryContainer = pp_theme_list_tertiaryContainer,
    onTertiaryContainer = pp_theme_list_onTertiaryContainer,
    error = pp_theme_list_error,
    errorContainer = pp_theme_list_errorContainer,
    onError = pp_theme_list_onError,
    onErrorContainer = pp_theme_list_onErrorContainer,
    background = pp_theme_list_background,
    onBackground = pp_theme_list_onBackground,
    surface = pp_theme_list_surface,
    onSurface = pp_theme_list_onSurface,
    surfaceVariant = pp_theme_list_surfaceVariant,
    onSurfaceVariant = pp_theme_list_onSurfaceVariant,
    outline = pp_theme_list_outline
/*    inverseOnSurface = pp_theme_list_inverseOnSurface,
    inverseSurface = pp_theme_list_inverseSurface,
    inversePrimary = pp_theme_list_inversePrimary,
    surfaceTint = pp_theme_list_surfaceTint,
    outlineVariant = pp_theme_list_outlineVariant,
    scrim = pp_theme_list_scrim*/
)

private val DarkColors = darkColorScheme(
    primary = pp_theme_dark_primary,
    onPrimary = pp_theme_dark_onPrimary,
    primaryContainer = pp_theme_dark_primaryContainer,
    onPrimaryContainer = pp_theme_dark_onPrimaryContainer,
    secondary = pp_theme_dark_secondary,
    onSecondary = pp_theme_dark_onSecondary,
    secondaryContainer = pp_theme_dark_secondaryContainer,
    onSecondaryContainer = pp_theme_dark_onSecondaryContainer,
    tertiary = pp_theme_dark_tertiary,
    onTertiary = pp_theme_dark_onTertiary,
    tertiaryContainer = pp_theme_dark_tertiaryContainer,
    onTertiaryContainer = pp_theme_dark_onTertiaryContainer,
    error = pp_theme_dark_error,
    errorContainer = pp_theme_dark_errorContainer,
    onError = pp_theme_dark_onError,
    onErrorContainer = pp_theme_dark_onErrorContainer,
    background = pp_theme_dark_background,
    onBackground = pp_theme_dark_onBackground,
    surface = pp_theme_dark_surface,
    onSurface = pp_theme_dark_onSurface,
    surfaceVariant = pp_theme_dark_surfaceVariant,
    onSurfaceVariant = pp_theme_dark_onSurfaceVariant,
    outline = pp_theme_dark_outline
/*    inverseOnSurface = pp_theme_dark_inverseOnSurface,
    inverseSurface = pp_theme_dark_inverseSurface,
    inversePrimary = pp_theme_dark_inversePrimary,
    surfaceTint = pp_theme_dark_surfaceTint,
    outlineVariant = pp_theme_dark_outlineVariant,
    scrim = pp_theme_dark_scrim*/
)

@Composable
fun PantryPalTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        /*dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }*/

        darkTheme -> DarkColors
        else -> LightColors

//        else -> LightColors
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}