package com.jefisu.data_remote.dto

import com.google.gson.annotations.SerializedName

data class BusLineDto(
    @SerializedName("cl") val idRoute: Int,
    @SerializedName("lc") val isCircularMode: Boolean,
    @SerializedName("lt") val line: String,
    @SerializedName("sl") val direction: Int,
    @SerializedName("tl") val lineModeIndicatorCode: Int,
    @SerializedName("tp") val departureTerminal: String,
    @SerializedName("ts") val arrivalTerminal: String
)