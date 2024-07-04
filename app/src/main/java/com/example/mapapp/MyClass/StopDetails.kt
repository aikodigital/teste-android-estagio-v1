package com.example.mapapp.ui.MyClass

data class StopDetails(
    // Código identificador da parada
    val cp: Int,

    // Nome da parada
    val np: String,

    // Informação de latitude da localização do veículo
    val py: Double,

    // Informação de longitude da localização do veículo
    val px: Double,

    // Relação de linhas localizadas onde:
    val l: List<Line>
)
