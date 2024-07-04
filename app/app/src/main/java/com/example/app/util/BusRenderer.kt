package com.example.app.util

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.app.R
import com.example.app.domain.model.Bus
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer

class BusRenderer(
    private val context: Context,
    map: GoogleMap,
    clusterManager: ClusterManager<Bus>
) : DefaultClusterRenderer<Bus>(context, map, clusterManager) {

    private val iconBusBitmap: BitmapDescriptor by lazy {
        val color = ContextCompat.getColor(
            context,
            R.color.icon_color
        )
        BitmapHelper.vectorToBitmap(
            context,
            R.drawable.ic_bus,
            color
        )
    }

    override fun onBeforeClusterItemRendered(
        item: Bus,
        markerOptions: MarkerOptions
    ) {
        markerOptions
            .title(item.busPrefix.toString())
            .position(LatLng(item.lat, item.lng))
            .icon(iconBusBitmap)
    }

    override fun onClusterItemRendered(clusterItem: Bus, marker: Marker) {
        marker.tag = clusterItem
    }
}