package com.example.app.domain.model

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

class LineAndBus(
    val fullPlacard: String,
    val busPrefix: Int,
    val lineOrigin: String,
    val lineDestination: String,
    val isAccessible: Boolean,
    val requestHour: String,
    val latLng: LatLng,
    val lineCode: Int
) : ClusterItem {
    override fun getPosition(): LatLng {
        return latLng
    }

    override fun getTitle(): String {
        return fullPlacard
    }

    override fun getSnippet(): String {
        return fullPlacard
    }
}
