package br.com.samuel.testeaiko.core.domain.dtos

import br.com.samuel.testeaiko.core.domain.model.BusLine
import com.fasterxml.jackson.annotation.JsonProperty

data class LineResponse(
    // Código identificador da linha. Este é um código identificador único de cada linha do sistema (por sentido de operação)
    @JsonProperty("cl")
    val cl: Int,
    // Indica se uma linha opera no modo circular (sem um terminal secundário)
    @JsonProperty("lc")
    val lc: Boolean,
    // Informa a primeira parte do letreiro numérico da linha
    @JsonProperty("lt")
    val lt: String,
    // Informa o sentido ao qual a linha atende, onde 1 significa Terminal Principal para Terminal Secundário e 2 para Terminal Secundário para Terminal Principal
    @JsonProperty("sl")
    val sl: Int,
    // Informa a segunda parte do letreiro numérico da linha, que indica se a linha opera nos modos:
    // BASE (10), ATENDIMENTO (21, 23, 32, 41)
    @JsonProperty("tl")
    val tl: Int,
    // Informa o letreiro descritivo da linha no sentido Terminal Principal para Terminal Secundário
    @JsonProperty("tp")
    val tp: String,
    // Informa o letreiro descritivo da linha no sentido Terminal Secundário para Terminal Principal
    @JsonProperty("ts")
    val ts: String
) {

    fun toModel() = BusLine(cl, lc, lt, sl, tl, tp, ts)

}
