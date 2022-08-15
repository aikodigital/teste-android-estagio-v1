package com.martini.spnoponto.domain.repositories

import com.martini.spnoponto.data.models.busStop.BusStopResponse
import com.martini.spnoponto.data.models.forecast.BusStopForecastResponse
import com.martini.spnoponto.data.models.line.LineSearchResponse
import com.martini.spnoponto.data.models.linePosition.LinePositionResponse
import com.martini.spnoponto.domain.entities.busStopForecast.BusStopForecastParams
import com.martini.spnoponto.domain.entities.linePosition.LinePositionParams
import com.martini.spnoponto.domain.entities.lineSearch.SearchLineParams
import com.martini.spnoponto.domain.entities.settings.Filter
import com.martini.spnoponto.domain.entities.settings.SetFilterSettingsParams

interface TrafficRepository {
    suspend fun searchLine(searchLineParams: SearchLineParams): LineSearchResponse

    suspend fun authorize()

    suspend fun getLinePosition(linePositionParams: LinePositionParams): LinePositionResponse

    suspend fun getBusStop(): BusStopResponse

    suspend fun getBusStopForecast(params: BusStopForecastParams) : BusStopForecastResponse

    suspend fun getFilterSettings(): Filter

    suspend fun setFilterSettings(params: SetFilterSettingsParams)
}