package com.aiko.domain.usecase

import com.aiko.domain.network.NetworkResult
import com.aiko.domain.repository.SPVisionRepository
import com.aiko.domain.usecase.base.BaseUseCase
import com.aiko.domain.usecase.base.DataResult

/**
 * Caso de uso responsável por realizar a autenticação do usuário.
 *
 * @property spVisionRepository Repositório responsável pela operação de autenticação.
 */
class PostAuthUseCase(
    private val spVisionRepository: SPVisionRepository
) : BaseUseCase<Unit, Boolean>() {
    override suspend fun doWork(value: Unit): DataResult<Boolean> {
        return try {
            val result = spVisionRepository.postAuth()
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
