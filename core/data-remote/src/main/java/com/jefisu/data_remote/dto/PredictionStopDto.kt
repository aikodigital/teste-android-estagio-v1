package com.jefisu.data_remote.dto

import com.google.gson.annotations.SerializedName

data class PredictionStopDto(
    @SerializedName("hr") val lastUpdate: String,
    @SerializedName("p") val stopDetails: PredictionStopDetailsDto
)

data class PredictionStopDetailsDto(
    @SerializedName("cp") val stopCode: Int,
    @SerializedName("l") val lines: List<LineDto>,
    @SerializedName("np") val stopName: String,
    @SerializedName("py") val latitude: Double,
    @SerializedName("px") val longitude: Double
)

