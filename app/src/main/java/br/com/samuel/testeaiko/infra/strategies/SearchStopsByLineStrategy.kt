package br.com.samuel.testeaiko.infra.strategies

import br.com.samuel.testeaiko.core.application.repositories.StopsRepository
import br.com.samuel.testeaiko.core.application.strategies.SearchStopsStrategy
import br.com.samuel.testeaiko.core.domain.model.BusStop
import br.com.samuel.testeaiko.util.ResourceResult
import javax.inject.Inject

class SearchStopsByLineStrategy @Inject constructor(
    private val stopsRepository: StopsRepository
) : SearchStopsStrategy {

    override suspend fun getStops(code: Int): ResourceResult<List<BusStop>> {
        return when (val result = stopsRepository.searchStopsByLine(code)) {
            is ResourceResult.Error -> ResourceResult.Error(result.error.orEmpty())
            is ResourceResult.Success -> {
                val stops = result.data?.map { it.toModel() }
                ResourceResult.Success(stops)
            }
        }
    }

}