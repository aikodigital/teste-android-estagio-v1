package br.com.samuel.testeaiko.core.application.repositories

import br.com.samuel.testeaiko.core.domain.dtos.CorridorResponse
import br.com.samuel.testeaiko.util.ResourceResult

interface CorridorsRepository {

    suspend fun searchCorridors(): ResourceResult<List<CorridorResponse>>

}