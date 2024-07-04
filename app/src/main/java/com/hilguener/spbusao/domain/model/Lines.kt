package com.hilguener.spbusao.domain.model

import com.google.gson.annotations.SerializedName

data class Lines(
    @SerializedName("cl")
    val codeOfLine: Int = 0,
    @SerializedName("lc")
    val worksInCircleMode: Boolean = false,
    // Informa a primeira parte do letreiro numérico da linha
    @SerializedName("lt")
    val firstPartOfSignLine: String = "",
    // Informa a segunda parte do letreiro numérico da linha, que indica se a linha opera nos modos:
    // BASE (10), ATENDIMENTO (21, 23, 32, 41)
    @SerializedName("tl")
    val secondPartOfSignLine: Int = 0,
    // Informa o sentido ao qual a linha atende,
    // onde 1 significa Terminal Principal para Terminal Secundário e 2 para Terminal Secundário para Terminal Principal
    @SerializedName("sl")
    val directionOfWorks: Int = 0,
    // Informa o letreiro descritivo da linha no sentido Terminal Principal para Terminal Secundário
    @SerializedName("tp")
    val signOfLineDirectionPrincipal: String = "",
    // Informa o letreiro descritivo da linha no sentido Terminal Secundário para Terminal Principal
    @SerializedName("ts")
    val signOfLineDirectionSecondary: String = "",
)
