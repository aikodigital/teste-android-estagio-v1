package br.com.samuel.testeaiko.core.application.repositories

import br.com.samuel.testeaiko.core.domain.dtos.BusLinePositionResponse
import br.com.samuel.testeaiko.util.ResourceResult

interface PositionsRepository {

    suspend fun searchByLine(lineCode: Int): ResourceResult<BusLinePositionResponse>

}