package com.aiko.bus.sptrans.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LineResponse(
    @SerialName("cl") val id: Int,
    @SerialName("lt") val identifierBegin: String,
    @SerialName("tl") val identifierEnd: Int,
    @SerialName("sl") val direction: Int,
    @SerialName("tp") val terminalPrimary: String,
    @SerialName("ts") val terminalSecondary: String,
)
