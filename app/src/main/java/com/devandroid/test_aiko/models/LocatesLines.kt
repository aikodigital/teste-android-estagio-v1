package com.devandroid.test_aiko.models

data class LocatesLines(
    val c: String, // line code
    val cl: Int, // full line code
    val sl : Int, // way
    val lt0: String, // origin name of line
    val lt1: String, // destiny name of the line
    val qv: Int,  // quantity of vehicles
    val vs : List<VehicleForecast> // list of Forecast Vehicle
)