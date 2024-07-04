package com.jefisu.home

import com.jefisu.domain.model.Line

sealed interface HomeAction {
    data class SearchBusLine(val busNumber: String) : HomeAction
    data class NavigateToBusStops(val busLine: Line) : HomeAction
}