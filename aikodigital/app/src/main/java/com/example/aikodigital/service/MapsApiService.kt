package com.example.aikodigital.service

import com.example.aikodigital.service.responses.maps.api_directions.MapsApiResponseList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MapsApiService {
    @GET("maps/api/directions/json")
    fun getRoutes(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("mode")mode: String,
        @Query("key") key: String
        ):Call<MapsApiResponseList>
}