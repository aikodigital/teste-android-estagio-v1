package com.jefisu.data_remote

import com.jefisu.data_remote.mapper.toBusStop
import com.jefisu.data_remote.mapper.toLine
import com.jefisu.data_remote.mapper.toPredictionStop
import com.jefisu.data_remote.mapper.toVehiclePositions
import com.jefisu.domain.BusConnectRepository
import com.jefisu.domain.BusConnectResult
import com.jefisu.domain.model.BusLine
import com.jefisu.domain.model.BusStop
import com.jefisu.domain.model.PredictionStop
import com.jefisu.domain.model.VehiclePositions
import com.jefisu.domain.util.Error
import com.jefisu.domain.util.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class BusConnectRepositoryImpl(
    private val api: SpTransApi,
    private val dispatcherIO: CoroutineDispatcher
) : BusConnectRepository {

    override suspend fun authenticate(): BusConnectResult<Unit> {
        return withContext(dispatcherIO) {
            try {
                val isAuthenticated = api.authenticate()
                if (!isAuthenticated) {
                    return@withContext Result.Error(ApiError.AUTHENTICATION_FAILED)
                }
                Result.Success(Unit)
            } catch (e: Exception) {
                Result.Error(e.toError())
            }
        }
    }

    override suspend fun getVehiclePositions(): BusConnectResult<VehiclePositions> {
        return withContext(dispatcherIO) {
            try {
                val data = api.getVehiclePositions().toVehiclePositions()
                Result.Success(data)
            } catch (e: Exception) {
                Result.Error(e.toError())
            }
        }
    }

    override suspend fun getBusLines(
        lineCodeOrName: String
    ): BusConnectResult<List<BusLine>> {
        return withContext(dispatcherIO) {
            try {
                val data = api.getBusLines(lineCodeOrName).map { it.toLine() }
                Result.Success(data)
            } catch (e: Exception) {
                Result.Error(e.toError())
            }
        }
    }

    override suspend fun getBusStops(lineCodeId: Int): BusConnectResult<List<BusStop>> {
        return withContext(dispatcherIO) {
            try {
                val data = api.getBusStops(lineCodeId).map { it.toBusStop() }
                Result.Success(data)
            } catch (e: Exception) {
                Result.Error(e.toError())
            }
        }
    }

    override suspend fun getBusArrivalPredictions(stopCode: Int): BusConnectResult<PredictionStop> {
        return withContext(dispatcherIO) {
            try {
                val data = api
                    .getBusArrivalPredictionsByStopCode(stopCode)
                    .toPredictionStop()
                Result.Success(data)
            } catch (e: Exception) {
                Result.Error(e.toError())
            }
        }
    }

    private fun Exception.toError(): Error {
        return when (this) {
            is IOException -> ApiError.NO_INTERNET
            is HttpException -> ApiError.SERVER_ERROR
            else -> Error.UNKNOWN
        }
    }
}