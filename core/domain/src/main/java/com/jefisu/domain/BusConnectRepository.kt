package com.jefisu.domain

import com.jefisu.domain.model.BusLine
import com.jefisu.domain.model.BusStop
import com.jefisu.domain.model.PredictionStop
import com.jefisu.domain.model.VehiclePositions
import com.jefisu.domain.util.Error
import com.jefisu.domain.util.Result

typealias BusConnectResult<T> = Result<T, Error>

interface BusConnectRepository {
    suspend fun authenticate(): BusConnectResult<Unit>
    suspend fun getVehiclePositions(): BusConnectResult<VehiclePositions>
    suspend fun getBusLines(lineCodeOrName: String): BusConnectResult<List<BusLine>>
    suspend fun getBusStops(lineCodeId: Int): BusConnectResult<List<BusStop>>
    suspend fun getBusArrivalPredictions(stopCode: Int): BusConnectResult<PredictionStop>
}