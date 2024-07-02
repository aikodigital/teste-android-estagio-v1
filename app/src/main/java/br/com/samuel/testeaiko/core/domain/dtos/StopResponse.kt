package br.com.samuel.testeaiko.core.domain.dtos

import br.com.samuel.testeaiko.core.domain.model.BusStop
import com.fasterxml.jackson.annotation.JsonProperty

data class StopResponse(
    // Código identificador da parada
    @JsonProperty("cp")
    val cp: Int,
    @JsonProperty("np")
    // Nome da parada
    val np: String,
    @JsonProperty("ed")
    //  Endereço de localização da parada
    val ed: String,
    // Informação de latitude da localização da parada
    @JsonProperty("py")
    val py: Double,
    // Informação de longitude da localização da parada
    @JsonProperty("px")
    val px: Double
) {

    fun toModel() = BusStop(cp, np, ed, py, px)

}