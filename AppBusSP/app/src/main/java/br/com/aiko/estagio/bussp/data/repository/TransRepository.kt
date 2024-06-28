package br.com.aiko.estagio.bussp.data.repository

interface TransRepository {

    suspend fun authentication(token: String): Boolean

}