package com.aiko.data.remote

import com.aiko.domain.model.LineModel
import com.aiko.domain.model.StopModel
import com.aiko.domain.model.ForecastModel
import com.aiko.domain.network.NetworkResult
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

const val token_key = "8df2ebbc7fed5723047bf5505576bc6f3986f9f6e43e1efc0b40086fc4e438c1"

/**
 * Interface que define os serviços de rede para o VisionService.
 */
interface VisionService {

    /**
     * Realiza a autenticação através de um token.
     *
     * @param token Token de autenticação utilizado na requisição. Um valor padrão é fornecido.
     * @return [NetworkResult] com o resultado da autenticação.
     */
    @POST("Login/Autenticar")
    suspend fun postAuth(@Query("token") token: String = token_key): NetworkResult<Boolean>

    /**
     * Busca paradas de ônibus com base em um termo de pesquisa.
     *
     * @param searchTerm Termo de busca utilizado para encontrar as paradas.
     * @return [NetworkResult] contendo a lista de paradas encontradas.
     */
    @GET("Parada/Buscar")
    suspend fun getStopsBySearchTerm(@Query("termosBusca") searchTerm: String): NetworkResult<List<StopModel>>

    /**
     * Obtém previsões de chegada de ônibus para uma parada específica.
     *
     * @param stopCode Código identificador da parada.
     * @return [NetworkResult] contendo as previsões para a parada.
     */
    @GET("Previsao/Parada")
    suspend fun getForecast(@Query("codigoParada") stopCode: Long): NetworkResult<ForecastModel>

    /**
     * Busca linhas de ônibus com base em um código ou número identificador.
     *
     * @param lineCode Código identificador da linha.
     * @return [NetworkResult] contendo a lista de linhas encontradas.
     */
    @GET("Linha/Buscar")
    suspend fun getLinesBySearchTerm(@Query("termosBusca") lineCode: Long): NetworkResult<List<LineModel>>
}