package com.tiagomaciel.olhovivo.api

import com.tiagomaciel.olhovivo.api.Cache.cookies
import okhttp3.Interceptor
import okhttp3.Response

class ReceivedCookiesInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())

        if (originalResponse.headers("Set-Cookie").isNotEmpty()) {
            originalResponse.headers("Set-Cookie").also {
                cookies = it
            }
        }

        return originalResponse
    }
}