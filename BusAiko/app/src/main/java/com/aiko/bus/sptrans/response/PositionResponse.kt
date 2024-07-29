package com.aiko.bus.sptrans.response

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class PositionResponseByLine(
    @SerialName("hr") val timeReference: String,
    @SerialName("vs") val vehicles: List<PositionResponseVehicle>
)

@Serializable
data class PositionResponseVehicle(
    @SerialName("p") val id: Int,
    @SerialName("a") val isAccessible: Boolean,
    @SerialName("py") val latitude: Double,
    @SerialName("px") val longitude: Double
)
