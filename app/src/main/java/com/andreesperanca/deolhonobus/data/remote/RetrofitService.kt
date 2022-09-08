package com.andreesperanca.deolhonobus.data.remote

import com.andreesperanca.deolhonobus.models.BusLine
import com.andreesperanca.deolhonobus.models.BusLinePosition
import com.andreesperanca.deolhonobus.models.BusStop
import com.andreesperanca.deolhonobus.models.BusStopPrediction
import com.andreesperanca.deolhonobus.util.API_KEY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RetrofitService {

    @POST("Login/Autenticar")
    fun getAuthInAPI(@Query("token") token: String = API_KEY) : Call<String>

    @GET("Linha/Buscar")
    fun getBusLinesWithNameOrNumber(@Query("termosBusca") termosBusca: String): Call<List<BusLine>>

    @GET("Parada/BuscarParadasPorLinha")
    fun getBusStopWithBusLineCode(@Query("codigoLinha") codigoLinha: String): Call<List<BusStop>>

    @GET("Previsao/Parada")
    fun getForecastWithBusStopCode(@Query("codigoParada") codigoParada: String) : Call<BusStopPrediction>

    @GET("Parada/Buscar")
    fun getBusStopWithAddressOrName(@Query("termosBusca") termosBusca: String): Call<List<BusStop>>

    @GET("Parada/BuscarParadasPorCorredor")
    fun getBusStopWithHallCode(@Query("codigoCorredor") codigoCorredor: String): Call<List<BusStop>>

    @GET("Posicao/Linha")
    fun getPositionBusLineWithBusLineCode(@Query("codigoLinha") codigoLinha: String): Call<BusLinePosition>

}