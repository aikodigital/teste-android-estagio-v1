package com.conti.onibusspemtemporeal.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BusRouteWithBus(

    @SerializedName("c")
    val fullPlacard: String,
    @SerializedName("cl")
    val lineCod: Int,
    @SerializedName("sl")
    val lineWay: Int,
    @SerializedName("lt0")
    val destinyPlacard: String,
    @SerializedName("lt1")
    val originPlacard: String,
    @SerializedName("qv")
    val amountBusFound: Int,
    @SerializedName("vs")
    val buses: List<Bus>

): Serializable
