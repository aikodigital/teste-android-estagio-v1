package com.jefisu.domain.model

data class Vehicle(
    val prefix: Int,
    val accessible: Boolean,
    val lastUpdate: String,
    val latitude: Double,
    val longitude: Double,
    val predictionTime: String?
)