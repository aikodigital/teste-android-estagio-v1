package com.aiko.bus.sptrans

import com.aiko.bus.sptrans.response.BusLaneResponse
import com.aiko.bus.sptrans.response.LineResponse
import com.aiko.bus.sptrans.response.PositionResponseByLine
import com.aiko.bus.sptrans.response.StopResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

object  SPTransClient {
    private val http: OkHttpClient = OkHttpClient()
    private const val BASE_URL = "https://aiko-olhovivo-proxy.aikodigital.io"

    suspend fun getVehiclePositionsByLane(lineId: Int): PositionResponseByLine {
        return makeGetRequest<PositionResponseByLine>("/Posicao/Linha?codigoLinha=${lineId}")
    }

    suspend fun getLines(q: String ): List<LineResponse> {
        return makeGetRequest<List<LineResponse>>("/Linha/Buscar?termosBusca=$q")
    }

    suspend fun getLineByBusLane(busLaneId: Int): List<LineResponse> {
        return makeGetRequest<List<LineResponse>>("/Linha/Buscar?codigoCorredor=$busLaneId")
    }

    suspend fun getBusLanes(): List<BusLaneResponse> {
        return makeGetRequest<List<BusLaneResponse>>("/Corredor")
    }

    suspend fun getStopByLine(lineId: Int): List<StopResponse> {
        return makeGetRequest<List<StopResponse>>("/Parada/BuscarParadasPorLinha?codigoLinha=$lineId")
    }

    suspend fun getStopByBusLane(busLaneId: Int): List<StopResponse> {
        return makeGetRequest<List<StopResponse>>("/Parada/BuscarParadasPorCorredor?codigoCorredor=$busLaneId")
    }

    private suspend inline fun <reified T> makeGetRequest(endpoint: String): T {
        return withContext(Dispatchers.IO) {
            val request = Request.Builder()
                .url("${BASE_URL}/${endpoint}")
                .build();

            http.newCall(request).execute().use { response ->
                decode<T>(response)
            }
        }
    }

    private inline fun <reified T> decode(response: Response): T {
        val json = Json { ignoreUnknownKeys = true }
        return json.decodeFromString<T>(response.body!!.string())
    }
}
