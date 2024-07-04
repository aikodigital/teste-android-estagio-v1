package com.exemple.urbanbus.data.repositories.stops

import com.exemple.urbanbus.data.models.BusStop
import com.exemple.urbanbus.data.models.BusStopLineArrival
import com.exemple.urbanbus.utils.UiState

interface BusStopRepository {
    suspend fun getBusStops(searchTerm: String = "", result: (UiState<List<BusStop>>) -> Unit)
    suspend fun getStopArrival(
        busStopCode: Number,
        result: (UiState<List<BusStopLineArrival>>) -> Unit
    )
}