package com.jefisu.data_remote.dto

import com.google.gson.annotations.SerializedName

data class LineDto(
    @SerializedName("c") val code: String,
    @SerializedName("cl") val lineCode: Int,
    @SerializedName("lt0") val origin: String,
    @SerializedName("lt1") val destination: String,
    @SerializedName("qv") val vehicleCount: Int,
    @SerializedName("sl") val terminalDirectionCode: Int,
    @SerializedName("vs") val vehicles: List<VehicleDto>
)