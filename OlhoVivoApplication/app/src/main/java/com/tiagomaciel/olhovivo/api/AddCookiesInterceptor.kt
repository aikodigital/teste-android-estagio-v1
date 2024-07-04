package com.tiagomaciel.olhovivo.api

import com.tiagomaciel.olhovivo.api.Cache.cookies
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AddCookiesInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()

        for (cookie in cookies) {
            builder.addHeader("Cookie", cookie)
        }

        return chain.proceed(builder.build())
    }
}

object Cache {
    var cookies: List<String> = emptyList()
}