package com.martini.spnoponto.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = darkPrimary,
    secondary = darkSecondary,
    background = darkBackground,
    surface = darkSurface,
    error = darkErrorColor
)

private val LightColorPalette = lightColors(
    primary = lightPrimary,
    secondary = lightSecondary,
    background = lightBackground,
    surface = lightSurface,
    error = lightErrorColor
)

@Composable
fun SPnoPontoTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}