package com.devandroid.test_aiko.services.repository

import com.devandroid.test_aiko.services.ApiService

class LiveEyeRepository(private val apiService : ApiService) {

    private val token = "82e31af3b774a240b144f4dae7dac3393ba7a9e78320dc87b12525b72fa0a645"
    private var authToken : String? = null

    suspend fun authenticate(token: String): Boolean {
        val response = apiService.authenticate(token)
        if (response.isSuccessful) {
            authToken = "Bearer $token" // Ajuste conforme necessário
            return response.body() == true
        }
        return false
    }

    suspend fun getPositionVehicle() = apiService.getPositionVehicle()
    suspend fun getStopPoints(stopPoint : String) = apiService.getStopPoints(stopPoint)
    suspend fun getArrivalForecast(stopCode : Int) = apiService.getArrivalForecast(stopCode)
    suspend fun getPositionWithLines(lineCode : Int) = apiService.getPositionWithLines(lineCode)
    suspend fun getLine(termSearch : String) = apiService.getLines(termSearch)
}