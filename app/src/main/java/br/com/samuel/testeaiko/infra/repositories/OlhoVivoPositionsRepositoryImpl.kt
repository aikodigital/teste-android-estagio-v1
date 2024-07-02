package br.com.samuel.testeaiko.infra.repositories

import android.util.Log
import br.com.samuel.testeaiko.core.application.repositories.PositionsRepository
import br.com.samuel.testeaiko.core.domain.dtos.BusLinePositionResponse
import br.com.samuel.testeaiko.infra.network.services.PositionsService
import br.com.samuel.testeaiko.util.ResourceResult
import retrofit2.HttpException
import retrofit2.Retrofit
import javax.inject.Inject

class OlhoVivoPositionsRepositoryImpl @Inject constructor(retrofit: Retrofit) :
    PositionsRepository {

    private val positionsService = retrofit.create(PositionsService::class.java)

    override suspend fun searchByLine(lineCode: Int): ResourceResult<BusLinePositionResponse> {
        return try {
            val response = positionsService.searchByLine(lineCode)
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
                "Failed to search vehicles by line: Line code ${lineCode}, status ${e.code()} - ${e.message()}"
            )
            return ResourceResult.Error("FAILED")
        }
    }

    companion object {
        private const val TAG = "OVPositionsRepository"
    }

}