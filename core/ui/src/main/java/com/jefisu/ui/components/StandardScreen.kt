package com.jefisu.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jefisu.common.UiText
import com.jefisu.ui.theme.Primary

@Composable
fun StandardScreen(
    error: UiText?,
    isLoading: Boolean,
    isOffline: Boolean,
    fixedContent: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        fixedContent()
        when {
            isOffline -> ErrorScreen(errorType = ErrorType.Offline)
            isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = Primary,
                    )
                }
            }

            error != null -> ErrorScreen(
                errorType = ErrorType.Dynamic(error.asString())
            )

            else -> content()
        }
    }
}
