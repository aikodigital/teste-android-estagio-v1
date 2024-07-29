package com.example.aikodigital.calls

import android.util.Log
import com.example.aikodigital.BuildConfig
import com.example.aikodigital.service.responses.corredores.CorredoresParadasResponse
import com.example.aikodigital.service.responses.corredores.CorredoresResponse
import com.example.aikodigital.service.responses.linhas.LinhasResponse
import com.example.aikodigital.service.responses.maps.api_directions.MapsApiResponseList
import com.example.aikodigital.service.responses.previsao_chegada.PrevisaoChegadaResponseList
import com.example.aikodigital.service.responses.previsao_chegada.parada.PrevisaoChegadaParadaResponse
import com.example.aikodigital.service.responses.previsao_chegada.parada.PrevisaoChegadaParadaResponseList
import com.example.aikodigital.service.responses.veiculos.VeiculosResponseList
import com.example.aikodigital.service.RetrofitFactory
import com.example.aikodigital.service.RetrofitFactoryMaps
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

suspend fun getLinhas(termoBusca: String): List<LinhasResponse> {
    return withContext(Dispatchers.IO) {
        try {
            val response = RetrofitFactory().linhasService().getLinhas(termoBusca).execute()
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                Log.e("LinesScreen", "$response")
                emptyList()
            }
        } catch (e: HttpException) {
            Log.e("LinesScreen", e.message())
            emptyList()
        } catch (e: Exception) {
            Log.e("LinesScreen", "${e.message}")
            emptyList()
        }
    }
}

suspend fun getVeiculosPorLinhas(codigoLinha: Int): VeiculosResponseList {
    return withContext(Dispatchers.IO) {
        try {
            val response =
                RetrofitFactory().veiculosService().getPosicaoVeiculos(codigoLinha).execute()

            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("BusStopScreen", "$response")
                VeiculosResponseList("", emptyList())
            }
        } catch (e: HttpException) {
            Log.e("BusStopScreen", "${e.message}")
            VeiculosResponseList("", emptyList())

        } catch (e: Exception) {
            Log.e("BusStopScreen", "${e.message}")
            VeiculosResponseList("", emptyList())

        } as VeiculosResponseList
    }
}

suspend fun getPrevisaoChegada(codigoLinha: Int): PrevisaoChegadaResponseList {
    return withContext(Dispatchers.IO) {
        try {
            val response =
                RetrofitFactory().previsaoChegadaService().getPrevisaoChegada(codigoLinha).execute()

            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("BusStopScreen", "$response")
                PrevisaoChegadaResponseList("", emptyList())
            }
        } catch (e: HttpException) {
            Log.e("BusStopScreen", "${e.message}")
            PrevisaoChegadaResponseList("", emptyList())

        } catch (e: Exception) {
            Log.e("BusStopScreen", "${e.message}")
            PrevisaoChegadaResponseList("", emptyList())

        } as PrevisaoChegadaResponseList
    }
}

suspend fun getPrevisaoChegadaParada(codigoParada: Int): PrevisaoChegadaParadaResponseList {
    return withContext(Dispatchers.IO) {
        try {
            val response =
                RetrofitFactory().previsaoChegadaService().getPrevisaoChegadaParada(codigoParada)
                    .execute()

            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("BusStopScreen", "$response")
                PrevisaoChegadaParadaResponseList(
                    "", PrevisaoChegadaParadaResponse(0, "", 0.0, 0.0, emptyList())
                )
            }
        } catch (e: HttpException) {
            Log.e("BusStopScreen", "${e.message}")
            PrevisaoChegadaParadaResponseList(
                "", PrevisaoChegadaParadaResponse(0, "", 0.0, 0.0, emptyList())
            )

        } catch (e: Exception) {
            Log.e("BusStopScreen", "${e.message}")
            PrevisaoChegadaParadaResponseList(
                "", PrevisaoChegadaParadaResponse(0, "", 0.0, 0.0, emptyList())
            )

        } as PrevisaoChegadaParadaResponseList
    }
}

suspend fun getCorredores(): List<CorredoresResponse> {
    return withContext(Dispatchers.IO) {
        try {
            val response = RetrofitFactory().corredoresService().getCorredores().execute()
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                Log.e("BusStopScreen", "$response")
                emptyList()
            }
        } catch (e: HttpException) {
            Log.e("BusStopScreen", "${e.message}")
            emptyList()
        } catch (e: Exception) {
            Log.e("BusStopScreen", "${e.message}")
            emptyList()
        }
    }
}

suspend fun getCorredoresParadas(codigoCorredor: Int): List<CorredoresParadasResponse> {
    return withContext(Dispatchers.IO) {
        try {
            val response =
                RetrofitFactory().corredoresService().getCorredoresParadas(codigoCorredor).execute()
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                Log.e("BusStopScreen", "$response")
                emptyList()
            }
        } catch (e: HttpException) {
            Log.e("BusStopScreen", "${e.message}")
            emptyList()
        } catch (e: Exception) {
            Log.e("BusStopScreen", "${e.message}")
            emptyList()
        }
    }
}

suspend fun getMapsApiRoute(origin: String, destination: String): MapsApiResponseList {
    return withContext(Dispatchers.IO) {
        try {
            val apiKey = BuildConfig.MAPS_API_KEY
            val response = RetrofitFactoryMaps().mapsService()
                .getRoutes(origin, destination, "walking", apiKey).execute()

            Log.i("erro", response.toString())
            if (response.isSuccessful) {
                Log.i("teste1", response.body().toString())
                response.body()
            } else {
                Log.e("BusStopScreen", "$response")
            }
        } catch (e: HttpException) {
            Log.e("BusStopScreen", "${e.message}")

        } catch (e: Exception) {
            Log.e("BusStopScreen", "${e.message}")
        } as MapsApiResponseList
    }
}

