package com.andreesperanca.deolhonobus.models

import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName

data class Localization(
    @SerializedName("p")
    val prefixVehicle: Int,
    @SerializedName("a")
    val accessibleForDisability: Boolean,
    @SerializedName("ta")
    val hourLastLocation: String,
    @SerializedName("py")
    val py: Double,
    @SerializedName("px")
    val px: Double
)