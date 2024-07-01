package com.conti.onibusspemtemporeal.data.models

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

class BusWithLine(
    val fullPlacard: String,
    val prefixBus: Int,
    val origin: String,
    val destiny: String,
    val accessibleBus: Boolean,
    val lastUpdate: String,
    val latLng: LatLng,
    val lineCod: Int
) : ClusterItem {
    override fun getPosition(): LatLng {
        return latLng
    }

    override fun getTitle(): String? {
        return fullPlacard
    }

    override fun getSnippet(): String? {
        return fullPlacard
    }
}