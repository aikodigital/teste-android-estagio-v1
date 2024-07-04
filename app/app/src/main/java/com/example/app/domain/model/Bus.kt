package com.example.app.domain.model

import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName
import com.google.maps.android.clustering.ClusterItem

data class Bus(
    @SerializedName("p")
    val busPrefix: Int,
    @SerializedName("a")
    val isAccessible: Boolean,
    @SerializedName("ta")
    val utcRequestHour: String,
    @SerializedName("py")
    val lat: Double,
    @SerializedName("px")
    val lng: Double,
    val sv: Any,
    val `is`: Any,
) : ClusterItem {
    override fun getPosition(): LatLng {
        return LatLng(lat, lng)
    }

    override fun getTitle(): String {
        return busPrefix.toString()
    }

    override fun getSnippet(): String {
        return busPrefix.toString()
    }
}