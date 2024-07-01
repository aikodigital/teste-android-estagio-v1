package com.conti.onibusspemtemporeal.data.retrofit.interfaces.services

import com.conti.onibusspemtemporeal.BuildConfig
import retrofit2.http.POST
import retrofit2.http.Query

interface OlhoVivoApiAuthenticateServiceInterface {

    @POST("Login/Autenticar")
    suspend fun postAuthenticate(
        @Query("token")
        token: String = BuildConfig.API_KEY
    ): Boolean

}