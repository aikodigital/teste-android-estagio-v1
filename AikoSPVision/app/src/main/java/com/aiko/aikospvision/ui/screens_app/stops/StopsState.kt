package com.aiko.aikospvision.ui.screens_app.stops

import com.aiko.domain.model.ForecastModel
import com.aiko.domain.model.StopModel

data class StopsState(
    val stopped: StopModel? = null,
    val forecast: ForecastModel? = null,
    val currentPlaceText: String = "",
    val destinationText: String = "",
    val listStopped: List<StopModel> = emptyList(),
    val onSearchClick: () -> Unit = {},
    val listFavorites: List<String> = emptyList(),
)