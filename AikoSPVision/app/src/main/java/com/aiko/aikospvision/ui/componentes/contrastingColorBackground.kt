package com.aiko.aikospvision.ui.componentes

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance

fun contrastingColorBackground(color: Color): Color {
    return if (color.luminance() > 0.5) Color.Black else Color.White
}