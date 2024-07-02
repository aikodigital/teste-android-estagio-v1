package br.com.samuel.testeaiko.ui.presentation.stops

import br.com.samuel.testeaiko.core.domain.model.BusStopForecast

data class ForecastResult(val stopCode: Int, val forecasts: List<BusStopForecast>)