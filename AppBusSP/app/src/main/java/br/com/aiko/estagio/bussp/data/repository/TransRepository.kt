package br.com.aiko.estagio.bussp.data.repository

import br.com.aiko.estagio.bussp.data.remote.response.Corredor
import br.com.aiko.estagio.bussp.data.remote.response.Empresas
import br.com.aiko.estagio.bussp.data.remote.response.Linha
import br.com.aiko.estagio.bussp.data.remote.response.Parada
import br.com.aiko.estagio.bussp.data.remote.response.PosVeiculo
import br.com.aiko.estagio.bussp.data.remote.response.Posicao
import retrofit2.Response
import retrofit2.http.Query

interface TransRepository {

    suspend fun authentication(token: String): Boolean

    suspend fun buscarLinha(termosBusca: String): List<Linha>

    suspend fun buscarLinhaSentido(termosBusca: String, sentido: Int): List<Linha>

    suspend fun buscarParada(parada: String): List<Parada>

    suspend fun buscarParadasPorLinha(codigoLinha: String): List<Parada>

    suspend fun buscarParadasPorCorredor(codigoCorredor: Int): List<Parada>

    suspend fun corredor(): List<Corredor>

    suspend fun empresas(): Empresas

    suspend fun posicao(): Posicao

    suspend fun posicaoLinha(codigoLinha: Int): PosVeiculo

    suspend fun posicaoGaragem(codigoEmpresa: Int, codigoLinha: Int): Posicao
}