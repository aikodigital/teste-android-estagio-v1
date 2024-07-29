package com.devandroid.test_aiko.models

data class LineForecast(
    val cp: Int, // code
    val np: String, // name
    val py : Double, // latitude
    val px : Double, // longitude
    val l: List<LocatesLines> // list vehiclesForecast
)
