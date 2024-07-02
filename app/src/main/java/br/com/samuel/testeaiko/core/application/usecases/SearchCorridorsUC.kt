package br.com.samuel.testeaiko.core.application.usecases

import br.com.samuel.testeaiko.core.application.repositories.CorridorsRepository
import br.com.samuel.testeaiko.core.domain.model.Corridor
import br.com.samuel.testeaiko.util.ResourceResult
import javax.inject.Inject

class SearchCorridorsUC @Inject constructor(private val corridorsRepository: CorridorsRepository) {

    suspend operator fun invoke(): ResourceResult<List<Corridor>> {
        return when (val result = corridorsRepository.searchCorridors()) {
            is ResourceResult.Error -> {
                ResourceResult.Error(result.error.orEmpty())
            }

            is ResourceResult.Success -> {
                ResourceResult.Success(result.data?.map { it.toModel() })
            }
        }
    }

}