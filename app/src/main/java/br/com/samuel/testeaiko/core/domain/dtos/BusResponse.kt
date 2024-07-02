package br.com.samuel.testeaiko.core.domain.dtos

import br.com.samuel.testeaiko.core.domain.model.BusPosition
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.ZonedDateTime

data class BusResponse(
    // Prefixo do veículo
    @JsonProperty("p")
    val p: Int,
    // Indica se o veículo é (true) ou não (false) acessível para pessoas com deficiência
    @JsonProperty("a")
    val a: Boolean,
    // Indica o horário universal (UTC) em que a localização foi capturada. Essa informação está no padrão ISO 8601
    @JsonProperty("ta")
    val ta: ZonedDateTime,
    //Informação de latitude da localização do veículo
    @JsonProperty("py")
    val py: Double,
    // Informação de longitude da localização do veículo
    @JsonProperty("px")
    val px: Double,
    // Desconhecido
    @JsonProperty("sv")
    val sv: String?,
    // Desconhecido
    @JsonProperty("is")
    val isp: String?
) {

    fun toModel() = BusPosition(p, a, ta, py, px)

}
