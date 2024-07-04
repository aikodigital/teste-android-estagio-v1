package com.example.mapapp.ui.MyClass

data class LineData(
    //Código identificador da linha. Este é um código identificador único de cada linha do
    // sistema (por sentido de operação)
    val cl: Int,

    // Indica se uma linha opera no modo circular (sem um terminal secundário)
    val lc: Boolean,

    //Informa a primeira parte do letreiro numérico da linha
    val lt: String,

    // Informa o sentido ao qual a linha atende, onde 1 significa Terminal Principal para
    // Terminal Secundário e 2 para Terminal Secundário para Terminal Principal
    val sl: Int,

    // Informa a segunda parte do letreiro numérico da linha, que indica se a linha opera nos modos:
    //BASE (10), ATENDIMENTO (21, 23, 32, 41)
    val tl: Int,

    // Informa o letreiro descritivo da linha no sentido Terminal Principal para Terminal Secundário
    val tp: String,

    // Informa o letreiro descritivo da linha no sentido Terminal Secundário para Terminal Principal
    val ts: String
)