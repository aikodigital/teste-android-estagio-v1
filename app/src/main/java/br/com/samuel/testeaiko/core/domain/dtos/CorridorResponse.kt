package br.com.samuel.testeaiko.core.domain.dtos

import br.com.samuel.testeaiko.core.domain.model.Corridor
import com.fasterxml.jackson.annotation.JsonProperty

data class CorridorResponse(
    // Código identificador da corredor. Este é um código identificador único de cada corredor inteligente do sistema
    @JsonProperty("cc")
    val cc: Int,
    // Nome do corredor
    @JsonProperty("nc")
    val nc: String
) {
    fun toModel() = Corridor(cc, nc)
}
