package com.andreesperanca.deolhonobus.models

data class ForecastVehicleView(
    val sign: String,
    val origin: String,
    val lineWay : Int,
    val destination: String,
    val vehicleList: List<ListOfVehiclesLocated>
)
