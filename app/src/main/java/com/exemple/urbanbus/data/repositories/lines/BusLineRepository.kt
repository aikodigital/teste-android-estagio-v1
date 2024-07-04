package com.exemple.urbanbus.data.repositories.lines

import com.exemple.urbanbus.data.models.BusLine
import com.exemple.urbanbus.data.models.VehicleArrival
import com.exemple.urbanbus.utils.UiState

interface BusLineRepository {
    suspend fun getBusLines(busStopCode: String, result: (UiState<List<BusLine>>) -> Unit)
    suspend fun getBusPosition(busLineCode: Int, result: (UiState<List<VehicleArrival>>) -> Unit)
}