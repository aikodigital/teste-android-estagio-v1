package com.example.spTransAiko2.model

import android.util.Log
import com.example.spTransAiko2.data.Posicao
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.String.valueOf


class apiResponse {


    //lateinit var retornoPosicao: Posicao
    val SP_TOKEN = "437def7fc37f10fad1a47cadb2ba195bf60b1a47bff8738270d55c3dac10fc06"
    val baseUrl = "http://api.olhovivo.sptrans.com.br/v2.1/"
    var cookie = ""
    //inteceptar o body do http

//    fun client () = OkHttpClient.Builder()
//        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
//        .build()

    //inteceptar o body do http
    var interceptor = HttpLoggingInterceptor()


    //client
    val client = OkHttpClient.Builder()
        .addInterceptor(interceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()
    val retrofit = Retrofit.Builder()
        .baseUrl("http://api.olhovivo.sptrans.com.br/v2.1")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    var service: apiOlhoVivo =  retrofit.create(apiOlhoVivo::class.java)
    var login: Call<String> = service.getLogin(SP_TOKEN)
    var res = login.enqueue(object : Callback<String?> {
        override fun onResponse(call: Call<String?>, response: Response<String?>) {

            cookie = valueOf(response.headers().get("Set-Cookie"))
            Log.w("Response login:", cookie)
            var posicao = service.getPosicao(cookie)
            Log.w("Response: Posicao", response.code().toString())

        }

        override fun onFailure(call: Call<String?>, t: Throwable) {
            Log.w("Response:",t.message.toString())

        }
    })


    var x = Log.w("getpos", valueOf(cookie))
    private var posicao = service.getPosicao(cookie)

    var setData = runBlocking {
        launch {
            delay(1000L)
            posicao?.enqueue(object : Callback<Posicao?> {
                override fun onResponse(call: Call<Posicao?>, response: Response<Posicao?>) {
                    //retornoPosicao = response.body()!!
                    Log.w("Response: Posicao", response.message().toString())

                }

                override fun onFailure(call: Call<Posicao?>, t: Throwable) {
                    Log.w("Response:",t.message.toString())

                }


            })

        }
    }
}