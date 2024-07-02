package br.com.samuel.testeaiko.core.application.repositories

import br.com.samuel.testeaiko.core.domain.dtos.StopResponse
import br.com.samuel.testeaiko.util.ResourceResult

interface StopsRepository {

    suspend fun searchStopsByLine(lineCode: Int): ResourceResult<List<StopResponse>>

    suspend fun searchStopsByCorridor(corridorCode: Int): ResourceResult<List<StopResponse>>

}