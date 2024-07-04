package com.jefisu.bus_stops

import com.jefisu.common.UiText
import com.jefisu.domain.model.BusStop
import com.jefisu.domain.model.PredictionStop

data class BusStopsState(
    val stops: List<BusStop> = emptyList(),
    val error: UiText? = null,
    val isStartDirection: Boolean = true,
    val predictionStop: PredictionStop? = null,
    val errorLoadPredictionStop: UiText? = null,
    val showMapView: Boolean = false,
    val lineCode: String = "",
)