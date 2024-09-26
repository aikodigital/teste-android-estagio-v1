package com.aiko.aikospvision.ui.componentes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun IndeterminateCircularIndicator(modifier: Modifier = Modifier, modifierBox: Modifier = Modifier.fillMaxSize()) {
    Box(
        modifier = modifierBox,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = modifier
                .size(64.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}