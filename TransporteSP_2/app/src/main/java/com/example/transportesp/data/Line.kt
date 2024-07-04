package com.example.transportesp.data

data class Line (
    val c: String,
    val cl: Int,
    val sl: Int,
    val tp: String,
    val ts: String,
    val qv: Int,
    val vs: List<Vehicle>
)