package br.com.samuel.testeaiko.core.application.repositories

import br.com.samuel.testeaiko.core.domain.dtos.LineResponse
import br.com.samuel.testeaiko.core.domain.enums.BusLineDirections
import br.com.samuel.testeaiko.util.ResourceResult

interface LinesRepository {

    suspend fun search(query: String): ResourceResult<List<LineResponse>>

    suspend fun searchDirection(
        query: String,
        direction: BusLineDirections
    ): ResourceResult<List<LineResponse>>

}