package com.example.mapapp.ui.MyClass

data class Line(
    // Letreiro completo
    val c: String,

    // Código identificador da linha
    val cl: Int,

    //  Sentido de operação onde 1 significa de Terminal Principal
    //  para Terminal Secundário e 2 de Terminal Secundário para Terminal Principal
    val sl: Int,

    // Letreiro de destino da linha
    val lt0: String,

    // Letreiro de origem da linha
    val lt1: String,

    //  Quantidade de veículos localizados
    val qv: Int,

    // Relação de veículos localizados onde:
    val vs: List<Vehicle>
)