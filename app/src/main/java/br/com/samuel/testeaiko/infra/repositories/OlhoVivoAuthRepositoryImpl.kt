package br.com.samuel.testeaiko.infra.repositories

import android.util.Log
import br.com.samuel.testeaiko.core.application.repositories.AuthRepository
import br.com.samuel.testeaiko.infra.network.services.AuthService
import br.com.samuel.testeaiko.util.ResourceResult
import retrofit2.HttpException
import retrofit2.Retrofit
import javax.inject.Inject

class OlhoVivoAuthRepositoryImpl @Inject constructor(retrofit: Retrofit) :
    AuthRepository {

    private val authService: AuthService = retrofit.create(AuthService::class.java)

    override suspend fun auth(token: String): ResourceResult<Boolean> {
        return try {
            val response = authService.auth(token)
            if (response.isSuccessful) {
                if (response.body()?.lowercase() == "false")
                    return ResourceResult.Error("UNAUTHORIZED");

                return ResourceResult.Success(true)
            }

            ResourceResult.Error("AUTH_FAILED")
        } catch (e: HttpException) {
            Log.e(TAG, "Failed to authenticate: ${e.code()} - ${e.message}")
            return ResourceResult.Error("AUTH_FAILED")
        }
    }

    companion object {
        private const val TAG = "OVAuthRepository"
    }

}