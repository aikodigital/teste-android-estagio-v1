package com.andreesperanca.deolhonobus.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Entity(tableName = "favoritesBusStop")
@Parcelize
data class BusStop(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @SerializedName("cp")
    val idCodeBusStop: Int,
    @SerializedName("np")
    val name: String,
    @SerializedName("ed")
    val address: String,
    @SerializedName("py")
    val latitude: Double,
    @SerializedName("px")
    val longitude: Double
) : Parcelable
