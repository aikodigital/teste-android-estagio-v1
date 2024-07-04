package com.jefisu.home.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jefisu.home.R
import com.jefisu.ui.theme.AppTheme
import com.jefisu.ui.theme.Primary
import com.jefisu.ui.theme.PrimaryLight
import com.jefisu.ui.theme.TextPrimary

@Composable
fun SearchTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholderText: String,
    modifier: Modifier = Modifier
) {
    val keyboardIsVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    val focusManager = LocalFocusManager.current
    LaunchedEffect(key1 = keyboardIsVisible) {
        if (!keyboardIsVisible) {
            focusManager.clearFocus()
        }
    }

    CompositionLocalProvider(
        LocalTextStyle provides AppTheme.typography.header3,
        LocalContentColor provides TextPrimary
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .border(
                    width = 1.dp,
                    color = PrimaryLight,
                    shape = CircleShape
                )
                .padding(AppTheme.spacing.medium)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = placeholderText,
                tint = Primary,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(AppTheme.spacing.small))
            Box {
                if (value.isBlank()) {
                    Text(
                        text = placeholderText,
                        color = PrimaryLight
                    )
                }
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = LocalTextStyle.current.copy(
                        color = LocalContentColor.current
                    ),
                    cursorBrush = SolidColor(LocalContentColor.current),
                    singleLine = true
                )
            }
        }
    }
}