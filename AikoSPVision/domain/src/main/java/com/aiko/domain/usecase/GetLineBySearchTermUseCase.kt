package com.aiko.domain.usecase

import com.aiko.domain.model.LineModel
import com.aiko.domain.network.NetworkResult
import com.aiko.domain.repository.SPVisionRepository
import com.aiko.domain.usecase.base.BaseUseCase
import com.aiko.domain.usecase.base.DataResult

/**
 * Caso de uso responsável por buscar linhas de ônibus com base em um código identificador.
 *
 * @property spVisionRepository Repositório responsável pelas operações de busca de linhas.
 */
class GetLineBySearchTermUseCase(
    private val spVisionRepository: SPVisionRepository
) : BaseUseCase<Long, List<LineModel>>() {
    override suspend fun doWork(value: Long): DataResult<List<LineModel>> {
        return try {
            val result = spVisionRepository.getLineBySearchTerm(value)
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
