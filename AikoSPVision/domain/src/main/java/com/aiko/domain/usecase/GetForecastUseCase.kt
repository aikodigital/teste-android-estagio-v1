package com.aiko.domain.usecase

import com.aiko.domain.model.ForecastModel
import com.aiko.domain.network.NetworkResult
import com.aiko.domain.repository.SPVisionRepository
import com.aiko.domain.usecase.base.BaseUseCaseFlow
import com.aiko.domain.usecase.base.DataResult

/**
 * Caso de uso responsável por obter as previsões de chegada de ônibus em uma parada específica.
 *
 * @property spVisionRepository Repositório responsável pelas operações de previsão de chegada.
 */
class GetForecastUseCase(
    private val spVisionRepository: SPVisionRepository
) : BaseUseCaseFlow<Long, DataResult<ForecastModel>>() {

    override suspend fun doWork(stopCode: Long): DataResult<ForecastModel> {
        return try {
            val result = spVisionRepository.getForecast(stopCode)
            when (result) {
                is NetworkResult.Success -> {
                    DataResult.Success(result.data)
                }
                is NetworkResult.Error -> {
                    DataResult.Failure(Throwable(result.body?.error))
                }
                is NetworkResult.Exception -> {
                    DataResult.Failure(result.e)
                }
            }
        } catch (e: Exception) {
            DataResult.Failure(e)
        }
    }
}