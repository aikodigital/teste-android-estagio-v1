package br.com.samuel.testeaiko.infra.repositories

import android.util.Log
import br.com.samuel.testeaiko.core.application.repositories.LinesRepository
import br.com.samuel.testeaiko.core.domain.dtos.LineResponse
import br.com.samuel.testeaiko.core.domain.enums.BusLineDirections
import br.com.samuel.testeaiko.infra.network.services.LinesService
import br.com.samuel.testeaiko.util.ResourceResult
import retrofit2.HttpException
import retrofit2.Retrofit
import javax.inject.Inject

class OlhoVivoLinesRepositoryImpl @Inject constructor(retrofit: Retrofit) : LinesRepository {

    private val linesService = retrofit.create(LinesService::class.java)

    override suspend fun search(query: String): ResourceResult<List<LineResponse>> {
        return try {
            val response = linesService.search(query)
            if (!response.isSuccessful) {
                return when (response.code()) {
                    401 -> ResourceResult.Error("UNAUTHORIZED")
                    else -> ResourceResult.Error("FAILED")
                }
            }

            ResourceResult.Success(response.body().orEmpty())
        } catch (e: HttpException) {
            Log.e(TAG, "Failed to search line: ${e.code()} - ${e.message}")
            return ResourceResult.Error("FAILED")
        }
    }

    override suspend fun searchDirection(
        query: String,
        direction: BusLineDirections
    ): ResourceResult<List<LineResponse>> {
        return try {
            val response = linesService.searchDirection(query, direction.value)
            if (!response.isSuccessful) {
                return when (response.code()) {
                    401 -> ResourceResult.Error("UNAUTHORIZED")
                    else -> ResourceResult.Error("FAILED")
                }
            }

            ResourceResult.Success(response.body().orEmpty())
        } catch (e: HttpException) {
            Log.e(TAG, "Failed to search line by direction: ${e.code()} - ${e.message}")
            return ResourceResult.Error("FAILED")
        }
    }

    companion object {
        private const val TAG = "OVLinesRepository"
    }

}