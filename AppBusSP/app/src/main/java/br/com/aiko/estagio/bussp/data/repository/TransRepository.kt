package br.com.aiko.estagio.bussp.data.repository

import br.com.aiko.estagio.bussp.data.remote.response.Corredor
import br.com.aiko.estagio.bussp.data.remote.response.Empresas
import br.com.aiko.estagio.bussp.data.remote.response.Linha
import br.com.aiko.estagio.bussp.data.remote.response.Parada
import retrofit2.Response

interface TransRepository {

    suspend fun authentication(token: String): Boolean

    suspend fun buscarLinha(termosBusca: String): List<Linha>

    suspend fun buscarLinhaSentido(termosBusca: String, sentido: Int): List<Linha>

    suspend fun buscarParada(parada: String): List<Parada>

    suspend fun buscarParadasPorLinha(codigoLinha: String): List<Parada>

    suspend fun buscarParadasPorCorredor(codigoCorredor: Int): List<Parada>

    suspend fun corredor(): List<Corredor>

    suspend fun empresas(): Empresas
}