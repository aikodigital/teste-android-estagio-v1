package com.aiko.aikospvision.ui.componentes

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aiko.aikospvision.R
import com.aiko.aikospvision.ui.theme.AikoSPVisionTheme


@Composable
fun InputSearchAppBar(
    textValue: String,
    onEvent: (String) -> Unit,
    singleLine: Boolean = true,
    modifier: Modifier = Modifier,
    textHint: String = "Search...",
    shape: Shape = RoundedCornerShape(20.dp),
    placeholder: @Composable (() -> Unit) = { Text(text = textHint) },
    colors: TextFieldColors = TextFieldDefaults.colors(
        cursorColor = Color.Black,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        disabledContainerColor = Color.Transparent,
        errorContainerColor = Color.Transparent,
    ),
) {
    TextField(
        value = textValue,
        onValueChange = onEvent,
        modifier = modifier.fillMaxWidth(),
        colors = colors,
        shape = shape,
        singleLine = singleLine,
        placeholder = placeholder
    )
}

@Preview(showBackground = true)
@Composable
fun InputSearchAppBarPreview() {
    AikoSPVisionTheme {
        InputSearchAppBar(
            textValue = "",
            onEvent = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}