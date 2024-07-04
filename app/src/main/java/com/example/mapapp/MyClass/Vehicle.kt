package com.example.mapapp.ui.MyClass

data class Vehicle(
    // Prefixo do veículo
    val p: String,

    // Horário previsto para chegada do veículo no ponto de parada relacionado
    val t: String,

    // Indica se o veículo é (true) ou não (false) acessível para pessoas com deficiência
    val a: Boolean,

    // Indica o horário universal (UTC) em que a localização foi capturada.
    // Essa informação está no padrão ISO 8601
    val ta: String,

    //  Informação de latitude da localização do veículo
    val py: Double,

    // Informação de longitude da localização do veículo
    val px: Double
)