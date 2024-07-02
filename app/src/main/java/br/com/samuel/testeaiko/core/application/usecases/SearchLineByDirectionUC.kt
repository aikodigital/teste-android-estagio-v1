package br.com.samuel.testeaiko.core.application.usecases

import br.com.samuel.testeaiko.core.application.repositories.LinesRepository
import br.com.samuel.testeaiko.core.domain.enums.BusLineDirections
import br.com.samuel.testeaiko.core.domain.model.BusLine
import br.com.samuel.testeaiko.util.ResourceResult
import javax.inject.Inject

class SearchLineByDirectionUC @Inject constructor(private val linesRepository: LinesRepository) {

    suspend operator fun invoke(
        query: String,
        direction: BusLineDirections
    ): ResourceResult<List<BusLine>> {
        return when (val result = linesRepository.searchDirection(query, direction)) {
            is ResourceResult.Error -> ResourceResult.Error(result.error.orEmpty())
            is ResourceResult.Success -> ResourceResult.Success(
                result.data.orEmpty().map { it.toModel() })
        }
    }

}
