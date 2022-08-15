package com.example.spTransAiko2.data

import okhttp3.Interceptor
import okhttp3.Response

class myInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Cookie","1dc7ee734f8103899947b6b22b2c4685e0098b4159ecd0cb2de443268fd20d3f")
            .build()
        return chain.proceed(request)
    }
}