package com.hilguener.spbusao.domain.model

import com.google.gson.annotations.SerializedName

data class PrevVehicle(
    @SerializedName("p")
    val prefixOfVehicle: String = "",
    @SerializedName("t")
    val expectedArrivalTime: String = "",
    @SerializedName("a")
    val isAccessible: Boolean = false,
    // Indica o horário universal (UTC) em que a localização foi capturada.
    // Essa informação está no padrão ISO 8601
    @SerializedName("ta")
    val captureTimeOfInformations: String = "",
    @SerializedName("px")
    val latitudeOfVehicle: Double = 0.0,
    @SerializedName("py")
    val longitudeOfVehicle: Double = 0.0,
)
