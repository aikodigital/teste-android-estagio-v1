package com.exemple.urbanbus.data.repositories.lines

import android.util.Log
import com.exemple.urbanbus.data.api.OlhoVivoAPI
import com.exemple.urbanbus.data.models.BusLine
import com.exemple.urbanbus.data.models.VehicleArrival
import com.exemple.urbanbus.data.repositories.authenticate.AuthenticateRepository
import com.exemple.urbanbus.utils.UiState
import retrofit2.HttpException
import java.io.IOException

class BusLineRepositoryImp(
    private val authRepository: AuthenticateRepository,
    private val olhoVivoAPI: OlhoVivoAPI
) : BusLineRepository {
    // metodo para busca de todas as linhas sendo obrigatorio a passagem do codigo da parada
    override suspend fun getBusLines(
        busStopCode: String,
        result: (UiState<List<BusLine>>) -> Unit
    ) {
        try {
            val response = olhoVivoAPI.getBusLines(authRepository.authenticate(), busStopCode)
            result.invoke(UiState.Success(response))
        } catch (networkError: IOException) {
            Log.d("app-error-lines", "Network error: $networkError")
            result.invoke(UiState.Failure.NetworkError("Check your network connection"))
        } catch (httpError: HttpException) {
            Log.d("app-error-lines", "HTTP error: $httpError")
            result.invoke(UiState.Failure.HttpError("There was an error with the HTTP request"))
        } catch (e: Exception) {
            Log.d("app-error-lines", "Error to get all lines: $e")
            result.invoke(UiState.Failure.UnknownError("There was an error retrieving the data."))
        }
    }

    // metodo para buscar posicoes dos onibus de uma determinada linha
    override suspend fun getBusPosition(
        busLineCode: Int,
        result: (UiState<List<VehicleArrival>>) -> Unit
    ) {
        try {
            val response = olhoVivoAPI.getBusPosition(authRepository.authenticate(), busLineCode)
            result.invoke(UiState.Success(response.vs))
        } catch (networkError: IOException) {
            Log.d("app-error-position", "Network error: $networkError")
            result.invoke(UiState.Failure.NetworkError("Check your network connection"))
        } catch (httpError: HttpException) {
            Log.d("app-error-position", "HTTP error: $httpError")
            result.invoke(UiState.Failure.HttpError("There was an error with the HTTP request"))
        } catch (e: Exception) {
            Log.d("app-error-position", "Error to get bus position: $e")
            result.invoke(UiState.Failure.UnknownError("There was an error retrieving the data."))
        }
    }
}