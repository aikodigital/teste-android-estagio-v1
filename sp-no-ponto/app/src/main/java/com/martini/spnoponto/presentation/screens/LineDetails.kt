package com.martini.spnoponto.presentation.screens

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import com.martini.spnoponto.presentation.components.lineDetails.appBar.LineDetailsAppBar
import com.martini.spnoponto.presentation.components.lineDetails.content.LineDetailsContent

@ExperimentalMaterialApi
@Composable
fun LineDetailsScreen() {
    Scaffold(
        topBar = { LineDetailsAppBar() },
        content = {
            LineDetailsContent(it)
        }
    )
}