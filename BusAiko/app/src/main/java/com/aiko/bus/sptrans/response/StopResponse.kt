package com.aiko.bus.sptrans.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StopResponse(
    @SerialName("cp") val id: Int,
    @SerialName("np") val name: String,
    @SerialName("py") val latitude: Double,
    @SerialName("px") val longitude: Double
)
