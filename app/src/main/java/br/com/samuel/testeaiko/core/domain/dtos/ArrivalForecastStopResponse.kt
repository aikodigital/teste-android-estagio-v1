package br.com.samuel.testeaiko.core.domain.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalTime

data class ArrivalForecastStopResponse(
    // Horário de referência da geração das informações
    @JsonProperty("hr")
    val hr: LocalTime,
    // Representa um ponto de parada
    @JsonProperty("p")
    val p: StopForecastResponse?
) {
}