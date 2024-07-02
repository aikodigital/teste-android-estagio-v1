package com.example.app.data.model

import com.google.gson.annotations.SerializedName

data class LineResponse(
    @SerializedName("c")
    val fullPlacard: String?,
    @SerializedName("cl")
    val lineCode: Int?,
    @SerializedName("sl")
    val lineDirection: Int?,
    @SerializedName("lt0")
    val lineDestination: String?,
    @SerializedName("lt1")
    val lineOrigin: String?,
    @SerializedName("qv")
    val numberBuses: Int?,
    @SerializedName("vs")
    val busList: List<BusResponse?>?
)