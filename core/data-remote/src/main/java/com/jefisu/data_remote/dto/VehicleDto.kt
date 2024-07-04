package com.jefisu.data_remote.dto

import com.google.gson.annotations.SerializedName

data class VehicleDto(
    @SerializedName("p") val prefix: Int,
    @SerializedName("a") val accessible: Boolean,
    @SerializedName("ta") val lastUpdate: String,
    @SerializedName("py") val latitude: Double,
    @SerializedName("px") val longitude: Double,
    @SerializedName("t") val predictionTime: String?
)