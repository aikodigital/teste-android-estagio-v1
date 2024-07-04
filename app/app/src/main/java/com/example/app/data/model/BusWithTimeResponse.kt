package com.example.app.data.model

import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName
import com.google.maps.android.clustering.ClusterItem

data class ApiResponse(
    @SerializedName("hr")
    val hour: String?,
    @SerializedName("p")
    val stopPoint: StopPointData?
)

data class StopPointData(
    @SerializedName("cp")
    val code: Int?,
    @SerializedName("np")
    val name: String?,
    @SerializedName("py")
    val latitude: Double?,
    @SerializedName("px")
    val longitude: Double?,
    @SerializedName("l")
    val lines: List<LineData>?
)

data class LineData(
    @SerializedName("c")
    val code: String?,
    @SerializedName("cl")
    val lineGroup: Int?,
    @SerializedName("sl")
    val direction: Int?,
    @SerializedName("lt0")
    val terminal0: String?,
    @SerializedName("lt1")
    val terminal1: String?,
    @SerializedName("qv")
    val vehicleCount: Int?,
    @SerializedName("vs")
    val busList: List<BusWithTimeResponse>?
)

data class BusWithTimeResponse(
    @SerializedName("p")
    val busPrefix: Int?,
    @SerializedName("t")
    val arrivalForecast: String?,
    @SerializedName("a")
    val isAccessible: Boolean?,
    @SerializedName("ta")
    val utcRequestHour: String?,
    @SerializedName("py")
    val lat: Double?,
    @SerializedName("px")
    val lng: Double?
) : ClusterItem {
    override fun getPosition(): LatLng {
        return LatLng(lat ?: 0.0, lng ?: 0.0)
    }

    override fun getTitle(): String {
        return busPrefix.toString()
    }

    override fun getSnippet(): String {
        return busPrefix.toString()
    }
}