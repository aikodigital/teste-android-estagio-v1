package com.exemple.urbanbus.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BusStop(
    @SerializedName("cp") val code: Number,
    @SerializedName("np") val name: String,
    @SerializedName("ed") val address: String,
    @SerializedName("py") val latitude: Double,
    @SerializedName("px") val longitude: Double
) : Parcelable
