package com.jefisu.ui.theme

import android.app.Activity
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

object AppTheme {
    val spacing = Spacing()
    val typography = Typography()
}

@Composable
fun BusConnectTheme(content: @Composable () -> Unit) {

    TransparentSystemBarColor()

    Surface(
        color = BackgroundPrimaryLight,
        contentColor = TextPrimary
    ) {
        ProvideTextStyle(
            value = AppTheme.typography.header3,
            content = content
        )
    }
}

@Composable
fun TransparentSystemBarColor(
    color: Color = Color.Transparent,
    isDarkIcons: Boolean = true
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.apply {
                statusBarColor = color.toArgb()
                navigationBarColor = statusBarColor
            }
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = isDarkIcons
            }
        }
    }
}