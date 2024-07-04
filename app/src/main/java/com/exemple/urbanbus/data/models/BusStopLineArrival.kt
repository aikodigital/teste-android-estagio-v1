package com.exemple.urbanbus.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BusStopLineArrival(
    @SerializedName("cl") val code: Int,
    @SerializedName("c") val sign: String,
    @SerializedName("sl") val direction: Int,
    @SerializedName("lt0") val mainTerminal: String,
    @SerializedName("lt1") val secondaryTerminal: String,
    @SerializedName("qv") val quantityVehicles: Int,
    @SerializedName("vs") val vehicleArrivals: List<VehicleArrival>,
    val destination: String? = null,
    val currentHour: String? = null
) : Parcelable