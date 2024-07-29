package com.devandroid.test_aiko.models

data class PositionLine(
    val hr : String, // time
    val vs: List<VehicleForecast>,
    val p : List<StopPoint>
)
