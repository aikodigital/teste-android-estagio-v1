package com.hilguener.spbusao.domain.usecase

import android.content.Context
import android.util.Log
import com.hilguener.spbusao.data.repository.HttpRepository

class AuthenticationUseCase(
    private val repo: HttpRepository,
) {
    suspend operator fun invoke(context: Context): Boolean {
        var isAuthenticate = false

        try {
            val certificate =
                repo.authenticator(context).headers().toMultimap()["Set-Cookie"]?.first()

            if (certificate != null) {
                repo.setCertificate(certificate)
                isAuthenticate = true
            } else {
                Log.d("HSV", "Cookie nulo")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("AuthenticationUseCase", "Exception: ${e.message}")
            isAuthenticate = false
        }

        return isAuthenticate
    }
}
