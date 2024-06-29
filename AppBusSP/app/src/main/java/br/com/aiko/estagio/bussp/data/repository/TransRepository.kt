package br.com.aiko.estagio.bussp.data.repository

import br.com.aiko.estagio.bussp.data.remote.response.Linha

interface TransRepository {

    suspend fun authentication(token: String): Boolean

    suspend fun buscarLinha(termosBusca: String): List<Linha>

    suspend fun buscarLinhaSentido(termosBusca: String, sentido: Int) : List<Linha>

}