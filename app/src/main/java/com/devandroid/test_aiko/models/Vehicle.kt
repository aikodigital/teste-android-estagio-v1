package com.devandroid.test_aiko.models

data class Vehicle(
    val p: Int,             // Prefixo do veículo
    val a: Boolean,         // Veículo acessível (true/false)
    val ta: String,         // Data e hora da posição
    val py: Double,         // Latitude
    val px: Double          // Longitude
)
