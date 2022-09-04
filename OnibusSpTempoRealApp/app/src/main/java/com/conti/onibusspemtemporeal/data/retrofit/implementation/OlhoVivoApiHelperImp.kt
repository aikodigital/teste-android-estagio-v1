package com.conti.onibusspemtemporeal.data.retrofit.implementation

import com.conti.onibusspemtemporeal.data.models.BusRoute
import com.conti.onibusspemtemporeal.data.retrofit.interfaces.OlhoVivoApiInterface
import com.conti.onibusspemtemporeal.data.retrofit.interfaces.services.OlhoVivoApiServiceInterface
import retrofit2.Response
import javax.inject.Inject

class OlhoVivoApiHelperImp @Inject constructor(
    private val apiOlhoVivoApiService: OlhoVivoApiServiceInterface
) : OlhoVivoApiInterface {

    override suspend fun getRoutes(searchTerm: String): Response<List<BusRoute>> = apiOlhoVivoApiService.getRoutes(searchTerm)

}