package com.martini.spnoponto.presentation.components.lineDetails.content

import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.martini.spnoponto.presentation.components.lineDetails.content.banner.LineDetailsBanner
import com.martini.spnoponto.presentation.components.lineDetails.content.info.LineDetailsMapInfo
import com.martini.spnoponto.presentation.components.lineDetails.content.maps.LineDetailsMap

@ExperimentalMaterialApi
@Composable
fun LineDetailsContent(
    paddingValues: PaddingValues
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
    ) {
        LineDetailsBanner()
        Spacer(modifier = Modifier.height(16.dp))
        LineDetailsMapInfo()
        LineDetailsMap()
    }
}