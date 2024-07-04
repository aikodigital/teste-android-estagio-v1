package com.example.app.domain.model

import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName
import com.google.maps.android.clustering.ClusterItem

data class StopPoint(
    @SerializedName("cp")
    val stopPointCode: Int,
    @SerializedName("np")
    val stopPointName: String,
    @SerializedName("ed")
    val stopPointAddress: String,
    @SerializedName("py")
    val lat: Double,
    @SerializedName("px")
    val lng: Double
) : ClusterItem {
    override fun getPosition(): LatLng {
        return LatLng(lat, lng)
    }

    override fun getTitle(): String {
        return stopPointName
    }

    override fun getSnippet(): String {
        return stopPointAddress
    }
}