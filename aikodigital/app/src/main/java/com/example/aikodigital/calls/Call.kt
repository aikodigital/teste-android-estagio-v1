package com.example.aikodigital.calls

import android.util.Log
import com.example.aikodigital.service.Response.corredores.CorredoresParadasResponse
import com.example.aikodigital.service.Response.corredores.CorredoresResponse
import com.example.aikodigital.service.Response.linhas.LinhasResponse
import com.example.aikodigital.service.Response.maps.api_directions.MapsApiResponse
import com.example.aikodigital.service.Response.maps.api_directions.MapsApiResponseList
import com.example.aikodigital.service.Response.paradas.ParadasResponse
import com.example.aikodigital.service.Response.previsao_chegada.PrevisaoChegadaResponseList
import com.example.aikodigital.service.Response.previsao_chegada.parada.PrevisaoChegadaParadaResponse
import com.example.aikodigital.service.Response.previsao_chegada.parada.PrevisaoChegadaParadaResponseList
import com.example.aikodigital.service.Response.veiculos.VeiculosResponseList
import com.example.aikodigital.service.RetrofitFactory
import com.example.aikodigital.service.RetrofitFactoryMaps
import com.google.maps.android.ktx.BuildConfig
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
                Log.e("LinesScreen", "Erro na resposta: ${response}")
                emptyList()
            }
        } catch (e: HttpException) {
            Log.e("LinesScreen", "Erro na chamada da API: ${e.message}")
            emptyList()
        } catch (e: Exception) {
            Log.e("LinesScreen", "Erro desconhecido: ${e.message}")
            emptyList()
        }
    }
}

suspend fun getParadasPorLinhas(codigoLinha: Int): List<ParadasResponse>{
    return  withContext(Dispatchers.IO){
        try{
            val response = RetrofitFactory().paradasService().getParadasPorLinha(codigoLinha).execute()
            if (response.isSuccessful){
                response.body() ?: emptyList()
            }else{
                Log.e("BusStopScreen", "Erro na resposta: ${response}")
                emptyList()
            }
        }catch (e: HttpException) {
            Log.e("BusStopScreen", "Erro na chamada da API: ${e.message}")
            emptyList()
        } catch (e: Exception) {
            Log.e("BusStopScreen", "Erro desconhecido: ${e.message}")
            emptyList()
        }
    }
}

suspend fun getVeiculosPorLinhas(codigoLinha: Int): VeiculosResponseList{
    return  withContext(Dispatchers.IO){
        try{
            val response = RetrofitFactory().veiculosService().getPosicaoVeiculos(codigoLinha).execute()

            if (response.isSuccessful){
                response.body()
            }else{
                Log.e("BusStopScreen", "Erro na resposta: ${response}")
                VeiculosResponseList("", emptyList())
            }
        }catch (e: HttpException) {
            Log.e("BusStopScreen", "Erro na chamada da API: ${e.message}")
            VeiculosResponseList("", emptyList())

        } catch (e: Exception) {
            Log.e("BusStopScreen", "Erro desconhecido: ${e.message}")
            VeiculosResponseList("", emptyList())

        } as VeiculosResponseList
    }
}
suspend fun getPrevisaoChegada(codigoLinha: Int): PrevisaoChegadaResponseList{
    return  withContext(Dispatchers.IO){
        try{
            val response = RetrofitFactory().previsaoChegadaService().getPrevisaoChegada(codigoLinha).execute()

            if (response.isSuccessful){
                response.body()
            }else{
                Log.e("BusStopScreen", "Erro na resposta: ${response}")
                PrevisaoChegadaResponseList("", emptyList())
            }
        }catch (e: HttpException) {
            Log.e("BusStopScreen", "Erro na chamada da API: ${e.message}")
            PrevisaoChegadaResponseList("", emptyList())

        } catch (e: Exception) {
            Log.e("BusStopScreen", "Erro desconhecido: ${e.message}")
            PrevisaoChegadaResponseList("", emptyList())

        } as PrevisaoChegadaResponseList
    }
}

suspend fun getPrevisaoChegadaParada(codigoParada: Int): PrevisaoChegadaParadaResponseList{
    return  withContext(Dispatchers.IO){
        try{
            val response = RetrofitFactory().previsaoChegadaService().getPrevisaoChegadaParada(codigoParada).execute()

            if (response.isSuccessful){
                response.body()
            }else{
                Log.e("BusStopScreen", "Erro na resposta: ${response}")
                PrevisaoChegadaParadaResponseList(
                    "", PrevisaoChegadaParadaResponse(0,"",0.0,0.0, emptyList())
                )
            }
        }catch (e: HttpException) {
            Log.e("BusStopScreen", "Erro na chamada da API: ${e.message}")
            PrevisaoChegadaParadaResponseList(
                "", PrevisaoChegadaParadaResponse(0,"",0.0,0.0, emptyList())
            )

        } catch (e: Exception) {
            Log.e("BusStopScreen", "Erro desconhecido: ${e.message}")
            PrevisaoChegadaParadaResponseList(
                "", PrevisaoChegadaParadaResponse(0,"",0.0,0.0, emptyList())
            )

        } as PrevisaoChegadaParadaResponseList
    }
}

suspend fun getCorredores(): List<CorredoresResponse>{
    return  withContext(Dispatchers.IO){
        try{
            val response = RetrofitFactory().corredoresService().getCorredores().execute()
            if (response.isSuccessful){
                response.body() ?: emptyList()
            }else{
                Log.e("BusStopScreen", "Erro na resposta: ${response}")
                emptyList()
            }
        }catch (e: HttpException) {
            Log.e("BusStopScreen", "Erro na chamada da API: ${e.message}")
            emptyList()
        } catch (e: Exception) {
            Log.e("BusStopScreen", "Erro desconhecido: ${e.message}")
            emptyList()
        }
    }
}

suspend fun getCorredoresParadas(codigoCorredor: Int): List<CorredoresParadasResponse>{
    return  withContext(Dispatchers.IO){
        try{
            val response = RetrofitFactory().corredoresService().getCorredoresParadas(codigoCorredor).execute()
            if (response.isSuccessful){
                response.body() ?: emptyList()
            }else{
                Log.e("BusStopScreen", "Erro na resposta: ${response}")
                emptyList()
            }
        }catch (e: HttpException) {
            Log.e("BusStopScreen", "Erro na chamada da API: ${e.message}")
            emptyList()
        } catch (e: Exception) {
            Log.e("BusStopScreen", "Erro desconhecido: ${e.message}")
            emptyList()
        }
    }
}

suspend fun getMapsApiRoute(origin: String, destination: String): MapsApiResponseList {
    return  withContext(Dispatchers.IO){
        try{
            val apiKey = com.example.aikodigital.BuildConfig.MAPS_API_KEY
            val response = RetrofitFactoryMaps().mapsService().getRoutes(origin, destination,"walking",apiKey).execute()

            Log.i("erro", response.toString())
            if (response.isSuccessful){
                Log.i("teste1", response.body().toString())
                response.body()
            }else{
                Log.e("BusStopScreen", "Erro na resposta: ${response}")
            }
        }catch (e: HttpException) {
            Log.e("BusStopScreen", "Erro na chamada da API: ${e.message}")

        } catch (e: Exception) {
            Log.e("BusStopScreen", "Erro desconhecido: ${e.message}")
        } as MapsApiResponseList
    }
}

