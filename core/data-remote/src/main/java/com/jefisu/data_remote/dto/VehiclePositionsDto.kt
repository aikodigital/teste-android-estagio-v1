package com.jefisu.data_remote.dto

import com.google.gson.annotations.SerializedName

data class VehiclePositionsDto(
    @SerializedName("hr") val lastUpdate: String,
    @SerializedName("l") val lines: List<LineDto>
)