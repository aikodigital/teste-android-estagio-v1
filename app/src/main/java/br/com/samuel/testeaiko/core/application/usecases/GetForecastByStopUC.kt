package br.com.samuel.testeaiko.core.application.usecases

import br.com.samuel.testeaiko.core.application.repositories.ArrivalForecastRepository
import br.com.samuel.testeaiko.core.domain.model.BusStopForecast
import br.com.samuel.testeaiko.util.ResourceResult
import javax.inject.Inject

class GetForecastByStopUC @Inject constructor(private val arrivalForecastRepository: ArrivalForecastRepository) {

    suspend operator fun invoke(stopCode: Int): ResourceResult<List<BusStopForecast>> {
        return when (val result = arrivalForecastRepository.getForecastByStop(stopCode)) {
            is ResourceResult.Error -> ResourceResult.Error(result.error.orEmpty())
            is ResourceResult.Success -> {
                val buses = result.data?.p?.l?.flatMap { l ->
                    l.vs.map { v ->
                        BusStopForecast(l.cl, result.data.p.cp, v.p, v.a, v.t, v.py, v.px)
                    }
                }

                ResourceResult.Success(buses)
            }
        }
    }

}