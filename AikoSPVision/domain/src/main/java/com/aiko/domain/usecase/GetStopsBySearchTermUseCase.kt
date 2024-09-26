package com.aiko.domain.usecase

import com.aiko.domain.model.StopModel
import com.aiko.domain.network.NetworkResult
import com.aiko.domain.repository.SPVisionRepository
import com.aiko.domain.usecase.base.BaseUseCase
import com.aiko.domain.usecase.base.DataResult

/**
 * Caso de uso responsável por buscar paradas de ônibus com base em um termo de pesquisa.
 *
 * @property spVisionRepository Repositório responsável pelas operações de busca de paradas.
 */
class GetStopsBySearchTermUseCase(
    private val spVisionRepository: SPVisionRepository
) : BaseUseCase<String, List<StopModel>>() {
    override suspend fun doWork(term: String): DataResult<List<StopModel>> {
        return try {
            val result = spVisionRepository.getStopsBySearchTerm(term)
            when (result) {
                is NetworkResult.Success -> DataResult.Success(result.data)
                is NetworkResult.Error -> DataResult.Failure(Throwable(result.body?.error))
                is NetworkResult.Exception -> DataResult.Failure(result.e)
            }
        } catch (e: Exception) {
            DataResult.Failure(e)
        }
    }
}