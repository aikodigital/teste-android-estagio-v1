package br.com.samuel.testeaiko.infra.repositories

import android.util.Log
import br.com.samuel.testeaiko.core.application.repositories.ArrivalForecastRepository
import br.com.samuel.testeaiko.core.domain.dtos.ArrivalForecastStopResponse
import br.com.samuel.testeaiko.core.domain.dtos.StopForecastResponse
import br.com.samuel.testeaiko.infra.network.services.ArrivalForecastService
import br.com.samuel.testeaiko.util.ResourceResult
import retrofit2.HttpException
import retrofit2.Retrofit
import javax.inject.Inject

class OlhoVivoArrivalForecastRepositoryImpl @Inject constructor(retrofit: Retrofit) :
    ArrivalForecastRepository {

    private val arrivalForecastService = retrofit.create(ArrivalForecastService::class.java)

    override suspend fun getForecastByStop(stopCode: Int): ResourceResult<ArrivalForecastStopResponse> {
        return try {
            val response = arrivalForecastService.getForecastByStop(stopCode)
            if (!response.isSuccessful) {
                return when (response.code()) {
                    401 -> ResourceResult.Error("UNAUTHORIZED")
                    else -> ResourceResult.Error("FAILED")
                }
            }

            ResourceResult.Success(response.body())
        } catch (e: HttpException) {
            Log.e(
                TAG,
                "Failed to search vehicles by line: Stop code ${stopCode}, status ${e.code()} - ${e.message()}"
            )
            ResourceResult.Error("FAILED")
        }
    }

    companion object {
        private const val TAG = "OVForecastRepository"
    }

}