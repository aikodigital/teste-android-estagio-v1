package com.example.transportesp.data

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

class VehicleClusterItem(
    private val position: LatLng,
    private val title: String
) : ClusterItem {

    override fun getPosition(): LatLng {
        return position
    }

    override fun getTitle(): String {
        return title
    }

    override fun getSnippet(): String? {
        return null
    }
}
