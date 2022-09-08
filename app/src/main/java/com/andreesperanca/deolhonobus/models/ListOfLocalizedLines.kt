package com.andreesperanca.deolhonobus.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListOfLocalizedLines(
    @SerializedName("c")
    val sign: String,
    @SerializedName("cl")
    val lineCode: Int,
    @SerializedName("sl")
    val lineWay: Int,
    @SerializedName("lt0")
    val destination: String,
    @SerializedName("lt1")
    val origin: String,
    @SerializedName("qv")
    val numberVehiclesLocated: String,
    @SerializedName("vs")
    val vehicleList: List<ListOfVehiclesLocated>
) : Parcelable
