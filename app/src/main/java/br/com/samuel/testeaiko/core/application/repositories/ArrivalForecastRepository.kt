package br.com.samuel.testeaiko.core.application.repositories

import br.com.samuel.testeaiko.core.domain.dtos.ArrivalForecastStopResponse
import br.com.samuel.testeaiko.util.ResourceResult

interface ArrivalForecastRepository {

    suspend fun getForecastByStop(stopCode: Int): ResourceResult<ArrivalForecastStopResponse>

}