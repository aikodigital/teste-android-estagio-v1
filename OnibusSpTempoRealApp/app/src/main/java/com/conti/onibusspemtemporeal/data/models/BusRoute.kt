package com.conti.onibusspemtemporeal.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BusRoute(
    @SerializedName("cl")
    val lineCod: Int,
    @SerializedName("lc")
    val circularMode: Boolean,
    @SerializedName("lt")
    val firstNumbersPlacard: String,
    @SerializedName("tl")
    val secondPartPlacard: Int,
    @SerializedName("sl")
    val lineWay: Int,
    @SerializedName("tp")
    val mainTerminal: String,
    @SerializedName("ts")
    val secondTerminal: String
): Serializable