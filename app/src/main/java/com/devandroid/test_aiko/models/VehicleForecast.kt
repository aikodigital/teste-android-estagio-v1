package com.devandroid.test_aiko.models

data class VehicleForecast(
    val p: Int, // prefix of the vehicle
    val t : String, // time
    val a : Boolean, // vechicle is acesible
    val ta: String, // time that the vehicle arrives
    val py : Double , // latitude
    val px : Double //longitude
)
