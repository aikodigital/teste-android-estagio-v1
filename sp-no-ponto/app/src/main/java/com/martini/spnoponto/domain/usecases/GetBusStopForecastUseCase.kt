package com.martini.spnoponto.domain.usecases

import com.martini.spnoponto.common.NoBusPointInfoException
import com.martini.spnoponto.data.models.forecast.toBusStopForecast
import com.martini.spnoponto.domain.entities.busStopForecast.BusStopForecast
import com.martini.spnoponto.domain.entities.busStopForecast.BusStopForecastParams
import com.martini.spnoponto.domain.repositories.TrafficRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.net.SocketTimeoutException
import javax.inject.Inject

class GetBusStopForecastUseCase @Inject constructor(
    private val trafficRepository: TrafficRepository
) {
    operator fun invoke(params: BusStopForecastParams) = flow {
        try {

            emit(GetBusStopForecastState.Loading)

            trafficRepository.authorize()

            val response = trafficRepository.getBusStopForecast(params)

            val forecast = response.toBusStopForecast()

            emit(GetBusStopForecastState.Loaded(forecast))

        } catch (e: NoBusPointInfoException) {
            emit(GetBusStopForecastState.NoBusInfoFailure)
        } catch (e: HttpException) {
            emit(GetBusStopForecastState.ServerFailure)
        } catch (e: SocketTimeoutException) {
            emit(GetBusStopForecastState.TimeoutFailure)
        } catch (e: Exception) {
            emit(GetBusStopForecastState.Failure)
        }
    }

    fun refresh(params: BusStopForecastParams) = flow {
        try {

            trafficRepository.authorize()

            val response = trafficRepository.getBusStopForecast(params)

            val forecast = response.toBusStopForecast()

            emit(GetBusStopForecastState.Loaded(forecast))

        } catch (e: NoBusPointInfoException) {
            emit(GetBusStopForecastState.NoBusInfoFailure)
        } catch (e: HttpException) {
            emit(GetBusStopForecastState.ServerFailure)
        } catch (e: SocketTimeoutException) {
            emit(GetBusStopForecastState.TimeoutFailure)
        } catch (e: Exception) {
            emit(GetBusStopForecastState.Failure)
        }
    }
}

sealed class GetBusStopForecastState {
    object Initial : GetBusStopForecastState()
    object Loading : GetBusStopForecastState()
    class Loaded(
        val busStopPoint: BusStopForecast
    ) : GetBusStopForecastState()

    object NoBusInfoFailure : GetBusStopForecastState()
    object ServerFailure : GetBusStopForecastState()
    object TimeoutFailure : GetBusStopForecastState()
    object Failure : GetBusStopForecastState()
}