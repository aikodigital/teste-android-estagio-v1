package br.com.samuel.testeaiko.core.domain.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalTime

data class BusLinePositionResponse(
    // Horário de referência da geração das informações
    @JsonProperty("hr")
    val hr: LocalTime,
    // Relação de veículos localizados
    @JsonProperty("vs")
    val vs: List<BusResponse>
)