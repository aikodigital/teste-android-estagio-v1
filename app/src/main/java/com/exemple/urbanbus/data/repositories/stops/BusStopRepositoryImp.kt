package com.exemple.urbanbus.data.repositories.stops

import android.util.Log
import com.exemple.urbanbus.data.api.OlhoVivoAPI
import com.exemple.urbanbus.data.models.BusStop
import com.exemple.urbanbus.data.models.BusStopLineArrival
import com.exemple.urbanbus.data.repositories.authenticate.AuthenticateRepository
import com.exemple.urbanbus.utils.UiState
import retrofit2.HttpException
import java.io.IOException

class BusStopRepositoryImp(
    private val authRepository: AuthenticateRepository,
    private val olhoVivoAPI: OlhoVivoAPI
) : BusStopRepository {
    // metodo para busca de todas as paradas sendo opcional a passagem de parametro
    override suspend fun getBusStops(searchTerm: String, result: (UiState<List<BusStop>>) -> Unit) {
        val busStops = mutableListOf<BusStop>()
        try {
            busStops.addAll(olhoVivoAPI.getBusStops(authRepository.authenticate(), searchTerm))
            result.invoke(UiState.Success(busStops))
        } catch (networkError: IOException) {
            Log.d("app-error-stops", "Network error: $networkError")
            result.invoke(UiState.Failure.NetworkError("Check your network connection"))
        } catch (httpError: HttpException) {
            Log.d("app-error-stops", "HTTP error: $httpError")
            result.invoke(UiState.Failure.HttpError("There was an error with the HTTP request"))
        } catch (e: Exception) {
            Log.d("app-error-stops", "Error to get all stops: $e")
            result.invoke(UiState.Failure.UnknownError("There was an error retrieving the data."))
        }
    }

    // metodo que realiza a busca de todas as previsoes para uma parada especifica
    override suspend fun getStopArrival(
        busStopCode: Number,
        result: (UiState<List<BusStopLineArrival>>) -> Unit
    ) {
        try {
            val stopArrival =
                olhoVivoAPI.getStopArrival(authRepository.authenticate(), busStopCode.toString())

            if (stopArrival.busStopData == null) {
                result.invoke(UiState.Success(listOf()))
                return
            }

            val busLineArrival: List<BusStopLineArrival> =
                stopArrival.busStopData.busLineArrival.map { line ->
                    if (line.direction == 1) {
                        line.copy(destination = line.mainTerminal, currentHour = stopArrival.hour)
                    } else {
                        line.copy(
                            destination = line.secondaryTerminal,
                            currentHour = stopArrival.hour
                        )
                    }
                }
            result.invoke(UiState.Success(busLineArrival))
        } catch (networkError: IOException) {
            Log.d("app-error-arrival", "Network error: $networkError")
            result.invoke(UiState.Failure.NetworkError("Check your network connection"))
        } catch (httpError: HttpException) {
            Log.d("app-error-arrival", "HTTP error: $httpError")
            result.invoke(UiState.Failure.HttpError("There was an error with the HTTP request"))
        } catch (e: Exception) {
            Log.d("app-error-arrival", "Error to get stop arrival: $e")
            result.invoke(UiState.Failure.UnknownError("There was an error retrieving the data."))
        }
    }
}