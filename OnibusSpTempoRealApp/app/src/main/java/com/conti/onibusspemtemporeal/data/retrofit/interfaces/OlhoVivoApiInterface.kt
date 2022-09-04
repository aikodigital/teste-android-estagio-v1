package com.conti.onibusspemtemporeal.data.retrofit.interfaces

import com.conti.onibusspemtemporeal.data.models.BusRoute
import retrofit2.Response

interface OlhoVivoApiInterface {

    suspend fun getRoutes(searchTerm: String): Response<List<BusRoute>>

}
