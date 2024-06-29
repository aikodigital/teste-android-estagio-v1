package br.com.aiko.estagio.bussp.data.remote

import br.com.aiko.estagio.bussp.data.remote.response.Linha
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val service: TransService
) {

    suspend fun authentication(token: String): Response<Boolean> {
        return service.authentication(token)
    }

    suspend fun buscarLinha(termosBusca: String): Response<List<Linha>> {
        return service.buscarLinha(termosBusca)
    }

    suspend fun buscarLinhaSentido(termosBusca: String, sentido: Int): Response<List<Linha>> {
        return service.buscarLinhaSentido(termosBusca, sentido)
    }
}