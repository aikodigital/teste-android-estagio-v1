package br.com.aiko.estagio.bussp.data.remote

import br.com.aiko.estagio.bussp.data.remote.response.Corredor
import br.com.aiko.estagio.bussp.data.remote.response.Empresas
import br.com.aiko.estagio.bussp.data.remote.response.Linha
import br.com.aiko.estagio.bussp.data.remote.response.Parada
import br.com.aiko.estagio.bussp.data.remote.response.PosVeiculo
import br.com.aiko.estagio.bussp.data.remote.response.Posicao
import br.com.aiko.estagio.bussp.data.remote.response.PrevisaoChegada
import br.com.aiko.estagio.bussp.data.remote.response.PrevisaoChegadaLinha
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

    suspend fun corredor(): Response<List<Corredor>> {
        return service.corredor()
    }

    suspend fun empresas(): Response<Empresas> {
        return service.empresas()
    }

    suspend fun posicao(): Response<Posicao> {
        return service.posicao()
    }

    suspend fun posicaoLinha(codigoLinha: Int): Response<PosVeiculo> {
        return service.posicaoLinha(codigoLinha)
    }

    suspend fun posicaoGaragem(codigoEmpresa: Int, codigoLinha: Int): Response<Posicao> {
        return service.posicaoGaragem(codigoEmpresa, codigoLinha)
    }

    suspend fun previsao(codigoParada: Int, codigoLinha: Int): Response<PrevisaoChegada> {
        return service.previsao(codigoParada, codigoLinha)
    }

    suspend fun previsaoLinha(codigoLinha: Int): Response<PrevisaoChegadaLinha> {
        return service.previsaoLinha(codigoLinha)
    }

    suspend fun previsaoParada(codigoParada: Int): Response<PrevisaoChegada> {
        return service.previsaoParada(codigoParada)
    }
}

