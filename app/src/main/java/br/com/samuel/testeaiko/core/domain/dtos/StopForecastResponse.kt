package br.com.samuel.testeaiko.core.domain.dtos

import com.fasterxml.jackson.annotation.JsonProperty

data class StopForecastResponse(
    // código identificador da parada
    @JsonProperty("cp")
    val cp: Int,
    // Nome da parada
    @JsonProperty("np")
    val np: String,
    // Informação de latitude da localização do veículo
    @JsonProperty("px")
    val py: Double,
    // Informação de longitude da localização do veículo
    @JsonProperty("py")
    val px: Double,
    // Relação de linhas localizadas
    @JsonProperty("l")
    val l: List<LineForecastResponse>
)