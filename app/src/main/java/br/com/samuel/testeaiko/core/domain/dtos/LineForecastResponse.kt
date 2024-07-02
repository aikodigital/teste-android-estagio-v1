package br.com.samuel.testeaiko.core.domain.dtos

import com.fasterxml.jackson.annotation.JsonProperty

data class LineForecastResponse(
    // Letreiro completo
    @JsonProperty("c")
    val c: String,
    // Código identificador da linha
    @JsonProperty("cl")
    val cl: Int,
    // Sentido de operação onde 1 significa de Terminal Principal para Terminal Secundário e 2 de Terminal Secundário para Terminal Principal
    @JsonProperty("sl")
    val sl: Int,
    // Letreiro de destino da linha
    @JsonProperty("lt0")
    val lt0: String,
    // Letreiro de origem da linha
    @JsonProperty("lt1")
    val lt1: String,
    // Quantidade de veículos localizados
    @JsonProperty("qv")
    val qv: Int,
    // Relação de veículos localizados
    @JsonProperty("vs")
    val vs: List<BusForecastResponse>
)
