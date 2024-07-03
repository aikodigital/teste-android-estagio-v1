package br.com.aiko.estagio.bussp.data.remote.di

import android.content.Context
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.net.SocketTimeoutException

class TimeoutInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return try {
            chain.proceed(chain.request())
        } catch (e: SocketTimeoutException) {
            Log.e("TimeoutInterceptor", "Timeout de conexão: ${e.message}")
            val errorMessage =
                "Timeout de conexão. Verifique sua conexão com a internet e tente novamente."
            Response.Builder().code(408) // Timeout HTTP Status Code
                .message(errorMessage).build()
        } catch (e: IOException) {
            Log.e("TimeoutInterceptor", "Erro de I/O: ${e.message}")
            throw e
        }
    }
}