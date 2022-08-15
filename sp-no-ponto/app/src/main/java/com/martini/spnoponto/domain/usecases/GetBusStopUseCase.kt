package com.martini.spnoponto.domain.usecases

import com.martini.spnoponto.data.models.busStop.toBusStop
import com.martini.spnoponto.domain.entities.busStop.BusStop
import com.martini.spnoponto.domain.repositories.TrafficRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.net.SocketTimeoutException
import javax.inject.Inject

class GetBusStopUseCase @Inject constructor(
    private val trafficRepository: TrafficRepository
) {
    operator fun invoke() = flow {
        try {
            trafficRepository.authorize()

            val response = trafficRepository.getBusStop()
            val stops = response.map { it.toBusStop() }

            emit(GetBusStopState.Loaded(stops))
        } catch (e: HttpException) {
            emit(GetBusStopState.ServerFailure)
        } catch (e: SocketTimeoutException) {
            emit(GetBusStopState.TimeoutFailure)
        } catch (e: Exception) {
            emit(GetBusStopState.Failure)
        }
    }
}

sealed class GetBusStopState {
    object Initial : GetBusStopState()
    object Loading : GetBusStopState()
    class Loaded(
        val stops: List<BusStop>
    ) : GetBusStopState()

    object ServerFailure : GetBusStopState()
    object TimeoutFailure : GetBusStopState()
    object Failure : GetBusStopState()
}