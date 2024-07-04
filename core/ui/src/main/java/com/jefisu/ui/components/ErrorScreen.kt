package com.jefisu.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.jefisu.ui.R
import com.jefisu.ui.theme.AppTheme
import com.jefisu.ui.theme.TextPrimary

@Composable
fun ErrorScreen(
    errorType: ErrorType,
    modifier: Modifier = Modifier
) {
    val message = if (errorType.message is String) {
        errorType.message
    } else {
        stringResource(id = errorType.message as Int)
    }
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = AppTheme.spacing.medium,
            alignment = Alignment.CenterVertically
        )
    ) {
        Image(
            painter = painterResource(id = errorType.imageResId),
            contentDescription = message
        )
        Text(
            text = message,
            style = AppTheme.typography.header2,
            color = TextPrimary,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    }
}

sealed class ErrorType(
    val message: Any,
    val imageResId: Int
) {
    data object Offline : ErrorType(
        message = R.string.offline_message,
        imageResId = R.drawable.no_internet
    )

    data object NoDataFound : ErrorType(
        message = R.string.no_data_message,
        imageResId = R.drawable.no_data_found
    )

    data class Dynamic(val error: String) : ErrorType(
        message = error,
        imageResId = R.drawable.no_data_found
    )
}