package com.aiko.bus.models

data class Line(
    val id: Int,
    val identifier: String,
    val direction: Int,
    val destination: String,
    val origin: String
)
