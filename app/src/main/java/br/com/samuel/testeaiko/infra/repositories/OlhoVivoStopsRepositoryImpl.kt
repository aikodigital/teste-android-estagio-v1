package br.com.samuel.testeaiko.infra.repositories

import android.util.Log
import br.com.samuel.testeaiko.core.application.repositories.StopsRepository
import br.com.samuel.testeaiko.core.domain.dtos.StopResponse
import br.com.samuel.testeaiko.infra.network.services.StopsService
import br.com.samuel.testeaiko.util.ResourceResult
import retrofit2.HttpException
import retrofit2.Retrofit
import javax.inject.Inject

class OlhoVivoStopsRepositoryImpl @Inject constructor(retrofit: Retrofit) : StopsRepository {

    private val stopsService = retrofit.create(StopsService::class.java)

    override suspend fun searchStopsByLine(lineCode: Int): ResourceResult<List<StopResponse>> {
        return try {
            val response = stopsService.searchByLine(lineCode)
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
                "Failed to search stops by line: Line code ${lineCode}, status ${e.code()} - ${e.message()}"
            )
            ResourceResult.Error("FAILED")
        }
    }

    override suspend fun searchStopsByCorridor(corridorCode: Int): ResourceResult<List<StopResponse>> {
        return try {
            val response = stopsService.searchByCorridor(corridorCode)
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
                "Failed to search stops by corridor: Corridor code ${corridorCode}, status ${e.code()} - ${e.message()}"
            )
            ResourceResult.Error("FAILED")
        }
    }

    companion object {
        private const val TAG = "OVStopsRepository"
    }

}