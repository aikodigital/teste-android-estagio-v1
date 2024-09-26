package com.aiko.domain.repository

import com.aiko.domain.model.LineModel
import com.aiko.domain.model.StopModel
import com.aiko.domain.model.ForecastModel
import com.aiko.domain.network.NetworkResult

/**
 * Interface que define os métodos do repositório para interagir com o serviço SPVision.
 */
interface SPVisionRepository {

    /**
     * Realiza a autenticação do usuário.
     *
     * @return [NetworkResult] com o resultado da autenticação.
     */
    suspend fun postAuth(): NetworkResult<Boolean>

    /**
     * Busca paradas de ônibus com base em um termo de pesquisa.
     *
     * @param term Termo de busca a ser utilizado para encontrar as paradas.
     * @return [NetworkResult] contendo a lista de paradas.
     */
    suspend fun getStopsBySearchTerm(term: String): NetworkResult<List<StopModel>>

    /**
     * Obtém previsões de chegada de ônibus em uma parada específica.
     *
     * @param stopCode Código identificador da parada.
     * @return [NetworkResult] contendo as previsões para a parada.
     */
    suspend fun getForecast(stopCode: Long): NetworkResult<ForecastModel>

    /**
     * Busca informações de linhas de ônibus com base em um termo de pesquisa.
     *
     * @param term Código ou número identificador da linha.
     * @return [NetworkResult] contendo a lista de linhas encontradas.
     */
    suspend fun getLineBySearchTerm(term: Long): NetworkResult<List<LineModel>>
}