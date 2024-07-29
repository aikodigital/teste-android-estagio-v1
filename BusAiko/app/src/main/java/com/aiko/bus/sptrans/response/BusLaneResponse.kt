package com.aiko.bus.sptrans.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BusLaneResponse(
    @SerialName("cc") val id: Int,
    @SerialName("nc") val name: String
)