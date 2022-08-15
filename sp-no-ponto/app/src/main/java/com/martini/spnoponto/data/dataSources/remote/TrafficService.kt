package com.martini.spnoponto.data.dataSources.remote

import com.martini.spnoponto.constants.Constants
import com.martini.spnoponto.data.models.busStop.BusStopResponse
import com.martini.spnoponto.data.models.forecast.BusStopForecastResponse
import com.martini.spnoponto.data.models.line.LineSearchResponse
import com.martini.spnoponto.data.models.linePosition.LinePositionResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface TrafficService {
    @GET(Constants.lineSearchUrl)
    suspend fun searchLine(
        @Query("termosBusca") text: String,
        @Query("token") apiKey: String
    ) : LineSearchResponse

    @POST(Constants.authorizeUrl)
    suspend fun authorize(
        @Query("token") apiKey: String
    )

    @GET(Constants.linePositionUrl)
    suspend fun getLinePosition(
        @Query("codigoLinha") code: Int,
        @Query("token") apiKey: String
    ): LinePositionResponse

    @GET(Constants.busStopUrl)
    suspend fun getBusStop(): BusStopResponse

    @GET(Constants.forecastUrl)
    suspend fun getBusStopForecast(
        @Query("codigoParada") code: Int,
        @Query("token") apiKey: String
    ) : BusStopForecastResponse
}