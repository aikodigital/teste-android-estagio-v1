package br.com.samuel.testeaiko.infra.repositories

import android.util.Log
import br.com.samuel.testeaiko.core.application.repositories.CorridorsRepository
import br.com.samuel.testeaiko.core.domain.dtos.CorridorResponse
import br.com.samuel.testeaiko.infra.network.services.CorridorsService
import br.com.samuel.testeaiko.util.ResourceResult
import retrofit2.HttpException
import retrofit2.Retrofit
import javax.inject.Inject

class OlhoVivoCorridorsRepositoryImpl @Inject constructor(retrofit: Retrofit) :
    CorridorsRepository {

    private val corridorsService = retrofit.create(CorridorsService::class.java)

    override suspend fun searchCorridors(): ResourceResult<List<CorridorResponse>> {
        return try {
            val response = corridorsService.searchCorridors()
            if (!response.isSuccessful) {
                return when (response.code()) {
                    401 -> ResourceResult.Error("UNAUTHORIZED")
                    else -> ResourceResult.Error("FAILED")
                }
            }

            ResourceResult.Success(response.body())
        } catch (e: HttpException) {
            Log.e(TAG, "Failed to search corridors: ${e.message()}")
            ResourceResult.Error("FAILED")
        }
    }

    companion object {
        private const val TAG = "OVCorridorsRepository"
    }

}