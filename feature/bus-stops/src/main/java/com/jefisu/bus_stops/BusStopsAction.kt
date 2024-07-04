package com.jefisu.bus_stops

sealed interface BusStopsAction {
    data object OpenMapView : BusStopsAction
    data class SelectBusStop(val stopIndex: Int) : BusStopsAction
    data object ChangeDirectionStops : BusStopsAction
    data object AddFavoriteBusLine : BusStopsAction
}