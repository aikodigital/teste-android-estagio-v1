package com.conti.onibusspemtemporeal.data.retrofit.interfaces

import com.conti.onibusspemtemporeal.data.models.BusRoute
import com.conti.onibusspemtemporeal.data.models.ResponseAllBus
import retrofit2.Response

interface OlhoVivoApiInterface {

    suspend fun getRoutes(searchTerm: String): Response<List<BusRoute>>

    suspend fun getAllBus(): Response<ResponseAllBus>

}
