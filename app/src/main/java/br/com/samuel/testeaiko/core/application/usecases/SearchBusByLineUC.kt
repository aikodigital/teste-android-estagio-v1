package br.com.samuel.testeaiko.core.application.usecases

import br.com.samuel.testeaiko.core.application.repositories.PositionsRepository
import br.com.samuel.testeaiko.core.domain.model.BusPosition
import br.com.samuel.testeaiko.util.ResourceResult
import javax.inject.Inject

class SearchBusByLineUC @Inject constructor(private val positionsRepository: PositionsRepository) {

    suspend operator fun invoke(lineCode: Int): ResourceResult<List<BusPosition>> {
        return when (val result = positionsRepository.searchByLine(lineCode)) {
            is ResourceResult.Error -> ResourceResult.Error(result.error.orEmpty())
            is ResourceResult.Success -> {
                ResourceResult.Success(result.data?.vs?.map { it.toModel() })
            }
        }
    }

}