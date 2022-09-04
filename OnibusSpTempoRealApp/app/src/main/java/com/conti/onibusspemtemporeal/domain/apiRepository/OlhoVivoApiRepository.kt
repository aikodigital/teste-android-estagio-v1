package com.conti.onibusspemtemporeal.domain.apiRepository

import com.conti.onibusspemtemporeal.data.models.BusRoute
import com.conti.onibusspemtemporeal.data.retrofit.implementation.OlhoVivoApiAuthenticateHelperImp
import com.conti.onibusspemtemporeal.data.retrofit.implementation.OlhoVivoApiHelperImp
import retrofit2.Response
import javax.inject.Inject

class OlhoVivoApiRepository @Inject constructor(
    private val olhoVivoApiHelperImp: OlhoVivoApiHelperImp,
    private val olhoVivoApiAuthenticateHelperImp: OlhoVivoApiAuthenticateHelperImp
) {

    suspend fun postAuthenticate(): Boolean = olhoVivoApiAuthenticateHelperImp.postAuthenticate()

    suspend fun getRoutes(searchTerm: String): Response<List<BusRoute>> =
        olhoVivoApiHelperImp.getRoutes(searchTerm)
}