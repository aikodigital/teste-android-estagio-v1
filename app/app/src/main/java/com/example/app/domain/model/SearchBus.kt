package com.example.app.domain.model

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

class SaearchBus(
    val busPrefix: Int,
    val isAccessible: Boolean,
    val utcRequestHour: String,
    val latLng: LatLng
) : ClusterItem {
    override fun getPosition(): LatLng {
        return latLng
    }

    override fun getTitle(): String {
        return busPrefix.toString()
    }

    override fun getSnippet(): String {
        return busPrefix.toString()
    }
}
