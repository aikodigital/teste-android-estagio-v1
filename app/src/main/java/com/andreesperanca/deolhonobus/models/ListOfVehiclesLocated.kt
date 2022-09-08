package com.andreesperanca.deolhonobus.models

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
    data class ListOfVehiclesLocated(
    @SerializedName("p")
    val prefixVehicle: Int,
    @SerializedName("t")
    val arrivalForecast: String,
    @SerializedName("a")
    val accessibleForDisability: Boolean,
    @SerializedName("ta")
    val hourLastLocation: String,
    @SerializedName("py")
    val py: Double,
    @SerializedName("px")
    val px: Double,
    val latLng: LatLng = LatLng(px,py)
) : Parcelable
