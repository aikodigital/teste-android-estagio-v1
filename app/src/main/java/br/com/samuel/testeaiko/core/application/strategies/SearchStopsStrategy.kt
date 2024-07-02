package br.com.samuel.testeaiko.core.application.strategies

import br.com.samuel.testeaiko.core.domain.model.BusStop
import br.com.samuel.testeaiko.util.ResourceResult

interface SearchStopsStrategy {
    suspend fun getStops(code: Int): ResourceResult<List<BusStop>>
}