package br.com.aiko.estagio.bussp.data.remote

import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val service: TransService
) {

    suspend fun authentication(token: String): Response<Boolean> {
        return service.authentication(token)
    }
}