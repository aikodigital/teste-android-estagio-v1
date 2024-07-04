package com.exemple.urbanbus.data.repositories.authenticate

import android.util.Log
import com.exemple.urbanbus.data.api.OlhoVivoAPI

class AuthenticateRepositoryImp(
    private val olhoVivoAPI: OlhoVivoAPI
) : AuthenticateRepository {
    // repository para request de autenticacao necessaria para buscar os cookies e realizar outras chamadas
    override suspend fun authenticate(): String {
        var cookie: String = ""
        try {
            val token = "a6004589148104e741826271ed9a2cc2c2a8af47a59707db26dbfb0a0a7ea5c6"
            val request = olhoVivoAPI.authenticate(token)
            cookie = request.headers().values("Set-Cookie").joinToString(";")
        } catch (e: Exception) {
            Log.e("test", e.toString())
        }
        return cookie
    }
}