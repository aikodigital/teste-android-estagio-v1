package com.martini.spnoponto.data.repositories

import com.martini.spnoponto.constants.Constants
import com.martini.spnoponto.data.dataSources.local.SettingsLocalDataSource
import com.martini.spnoponto.data.models.busStop.BusStopResponse
import com.martini.spnoponto.data.models.forecast.BusStopForecastResponse
import com.martini.spnoponto.data.models.line.LineSearchResponse
import com.martini.spnoponto.data.models.linePosition.LinePositionResponse
import com.martini.spnoponto.data.dataSources.remote.TrafficService
import com.martini.spnoponto.domain.entities.busStopForecast.BusStopForecastParams
import com.martini.spnoponto.domain.entities.linePosition.LinePositionParams
import com.martini.spnoponto.domain.entities.lineSearch.SearchLineParams
import com.martini.spnoponto.domain.entities.settings.Filter
import com.martini.spnoponto.domain.entities.settings.SetFilterSettingsParams
import com.martini.spnoponto.domain.repositories.TrafficRepository
import javax.inject.Inject

class TrafficRepositoryImpl @Inject constructor(
    private val trafficService: TrafficService,
    private val settingsLocalDataSource: SettingsLocalDataSource
) : TrafficRepository {
    override suspend fun searchLine(searchLineParams: SearchLineParams): LineSearchResponse {
        return trafficService.searchLine(
            searchLineParams.text,
            Constants.apiKey
        )
    }

    override suspend fun authorize() {
        return trafficService.authorize(Constants.apiKey)
    }

    override suspend fun getLinePosition(linePositionParams: LinePositionParams): LinePositionResponse {
        return trafficService.getLinePosition(
            linePositionParams.code,
            Constants.apiKey
        )
    }

    override suspend fun getBusStop(): BusStopResponse {
        return trafficService.getBusStop()
    }

    override suspend fun getBusStopForecast(params: BusStopForecastParams): BusStopForecastResponse {
        return trafficService.getBusStopForecast(
            params.busStop.code,
            Constants.apiKey
        )
    }

    override suspend fun getFilterSettings(): Filter {
        return settingsLocalDataSource.getFilterSettings()
    }

    override suspend fun setFilterSettings(params: SetFilterSettingsParams) {
        return settingsLocalDataSource.setFilterSettings(params)
    }
}