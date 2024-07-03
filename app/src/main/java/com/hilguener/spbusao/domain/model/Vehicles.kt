package com.hilguener.spbusao.domain.model

import com.google.gson.annotations.SerializedName

data class Vehicles(
    @SerializedName("p")
    val prefixOfVehicle: String = "",
    @SerializedName("a")
    val isAccessible: Boolean = false,
    @SerializedName("ta")
    val hourOfCapture: String = "",
    @SerializedName("py")
    val latitude: Double = 0.0,
    @SerializedName("px")
    val longitude: Double = 0.0,
)
