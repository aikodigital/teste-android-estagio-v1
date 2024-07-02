package com.example.app.domain.model

import com.google.gson.annotations.SerializedName

data class Line(
    @SerializedName("c")
    val fullPlacard: String,
    @SerializedName("cl")
    val lineCode: Int,
    @SerializedName("sl")
    val lineDirection: Int,
    @SerializedName("lt0")
    val lineDestination: String,
    @SerializedName("lt1")
    val lineOrigin: String,
    @SerializedName("qv")
    val numberBuses: Int,
    @SerializedName("vs")
    val busList: List<Bus?>
)