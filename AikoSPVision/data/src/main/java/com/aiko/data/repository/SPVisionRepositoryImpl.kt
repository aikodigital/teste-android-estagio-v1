package com.aiko.data.repository

import com.aiko.data.remote.VisionService
import com.aiko.domain.model.LineModel
import com.aiko.domain.model.StopModel
import com.aiko.domain.model.ForecastModel
import com.aiko.domain.network.ErrorBody
import com.aiko.domain.network.NetworkResult
import com.aiko.domain.repository.SPVisionRepository

/**
 * Implementação do repositório SPVision para acessar os serviços da API.
 *
 * @param visionService Instância do [VisionService] utilizada para realizar as chamadas de rede.
 */
class SPVisionRepositoryImpl(private val visionService: VisionService) : SPVisionRepository {

    override suspend fun postAuth(): NetworkResult<Boolean> {
        return when (val result = visionService.postAuth()) {
            is NetworkResult.Success -> NetworkResult.Success(result.data)
            is NetworkResult.Exception -> NetworkResult.Exception(Error("${result.e}"))
            is NetworkResult.Error -> NetworkResult.Error(
                code = 0,
                ErrorBody(result.body?.error ?: "UNKNOWN_MESSAGE_ERROR")
            )
        }
    }

    override suspend fun getStopsBySearchTerm(term: String): NetworkResult<List<StopModel>> {
        return when (val result = visionService.getStopsBySearchTerm(searchTerm = term)) {
            is NetworkResult.Success -> NetworkResult.Success(result.data)
            is NetworkResult.Exception -> NetworkResult.Exception(Error("${result.e}"))
            is NetworkResult.Error -> NetworkResult.Error(
                code = 0,
                ErrorBody(result.body?.error ?: "UNKNOWN_MESSAGE_ERROR")
            )
        }
    }

    override suspend fun getForecast(stopCode: Long): NetworkResult<ForecastModel> {
        return when (val result = visionService.getForecast(stopCode)) {
            is NetworkResult.Success -> NetworkResult.Success(result.data)
            is NetworkResult.Exception -> NetworkResult.Exception(Error("${result.e}"))
            is NetworkResult.Error -> NetworkResult.Error(
                code = 0,
                ErrorBody(result.body?.error ?: "UNKNOWN_MESSAGE_ERROR")
            )
        }
    }

    override suspend fun getLineBySearchTerm(term: Long): NetworkResult<List<LineModel>> {
        return when (val result = visionService.getLinesBySearchTerm(lineCode = term)) {
            is NetworkResult.Success -> NetworkResult.Success(result.data)
            is NetworkResult.Exception -> NetworkResult.Exception(Error("${result.e}"))
            is NetworkResult.Error -> NetworkResult.Error(
                code = 0,
                ErrorBody(result.body?.error ?: "UNKNOWN_MESSAGE_ERROR")
            )
        }
    }
}