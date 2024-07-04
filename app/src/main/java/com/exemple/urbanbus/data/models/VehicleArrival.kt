package com.exemple.urbanbus.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class VehicleArrival(
    @SerializedName("p") val prefix: String,
    @SerializedName("t") val time: String = "",
    @SerializedName("a") val accessible: Boolean,
    @SerializedName("ta") val updateTime: String,
    @SerializedName("py") val latitude: Double,
    @SerializedName("px") val longitude: Double,
    @Transient val sv: String? = null,
    @SerializedName("is") @Transient val ignoredIs: String? = null
) : Parcelable