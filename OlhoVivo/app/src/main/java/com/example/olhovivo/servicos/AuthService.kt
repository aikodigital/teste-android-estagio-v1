package com.example.olhovivo.network

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class AuthService {
    fun authenticate(token: String, callback: (Boolean) -> Unit) {
        val url = "http://api.olhovivo.sptrans.com.br/v2.1/Login/Autenticar?token=$token"
        val request = Request.Builder()
            .url(url)
            .post(RequestBody.create(null, ""))
            .build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                callback(false)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseData = response.body?.string()
                    callback(responseData == "true")
                } else {
                    callback(false)
                }
            }
        })
    }
}
