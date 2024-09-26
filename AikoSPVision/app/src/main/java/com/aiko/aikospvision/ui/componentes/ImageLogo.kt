package com.aiko.aikospvision.ui.componentes

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun ImageLogo(
    modifier: Modifier = Modifier
        .size(width = 150.dp, height = 150.dp)
        .padding(top = 20.dp),
    darkMode: Boolean = isSystemInDarkTheme(),
    imageLight: Painter = painterResource(id = android.R.drawable.ic_menu_gallery),
    imageDark: Painter = painterResource(id = android.R.drawable.ic_menu_gallery),
    description: String = "Logo Image"
) {
    val painter: Painter = if (darkMode) {
        imageDark
    } else {
        imageLight
    }

    Image(
        painter = painter,
        contentDescription = description,
        modifier = modifier
    )
}