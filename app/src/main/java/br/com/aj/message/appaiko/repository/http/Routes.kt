package br.com.aj.message.appaiko.repository.http

import android.content.Context
import br.com.aj.message.appaiko.data.*
import com.google.gson.Gson
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface Routes {

    @POST("/Login/Autenticar")
    suspend fun autenticar(@Query("token") token: String): Boolean

    @GET("/Posicao")
    suspend fun all(): PositionVehicles

    @GET("/Corredor")
    suspend fun corrredor(): Corredor

    @GET("/Previsao/Parada")
    suspend fun previsionBus(@Query("codigoParada") codigoParada: String): PrevisaoParada

    @GET("/Parada/BuscarParadasPorCorredor")
    suspend fun buscarParadasPorCorredor(@Query("codigoCorredor") codigoCorredor: String):
            BuscarParadasPorCorredor

    @GET("/maps/api/directions/json")
    suspend fun mapRoute(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("sensor") sensor: String,
        @Query("mode") mode: String,
        @Query("key") key: String

    ): MapData.MapData

    class Cookies(val ctx: Context) : CookieJar {

        var cookies = mutableListOf<Cookie>()

        val shred = ctx.getSharedPreferences("appaiko", Context.MODE_PRIVATE)

        override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
            this.cookies.addAll(cookies)
            shred.edit().putString("cookie", Gson().toJson(this.cookies)).apply()
        }

        override fun loadForRequest(url: HttpUrl): MutableList<Cookie> {
            return this.cookies
        }


    }

}