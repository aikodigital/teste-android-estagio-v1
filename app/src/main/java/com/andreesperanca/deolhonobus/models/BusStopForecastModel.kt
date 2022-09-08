package com.andreesperanca.deolhonobus.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BusStopForecastModel (
    @SerializedName("cp")
    val id: Int,
    @SerializedName("np")
    val name: String,
    @SerializedName("py")
    val latitude: Double,
    @SerializedName("px")
    val longitude: Double,
    @SerializedName("l")
    val listOfLinesFound: List<ListOfLocalizedLines>
): Parcelable