package br.com.aiko.estagio.bussp.data.remote

import br.com.aiko.estagio.bussp.data.remote.response.Linha
import br.com.aiko.estagio.bussp.data.remote.response.Parada
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Query
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

    suspend fun buscarParada(parada: String): Response<List<Parada>> {
        return service.buscarParada(parada)
    }

    suspend fun buscarParadasPorLinha(codigoLinha: String): Response<List<Parada>> {
        return service.buscarParadasPorLinha(codigoLinha)
    }

    suspend fun buscarParadasPorCorredor(codigoCorredor: Int): Response<List<Parada>> {
        return service.buscarParadasPorCorredor(codigoCorredor)
    }
}

