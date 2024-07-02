package br.com.samuel.testeaiko.core.application.repositories

import br.com.samuel.testeaiko.util.ResourceResult

interface AuthRepository {

    suspend fun auth(token: String): ResourceResult<Boolean>

}