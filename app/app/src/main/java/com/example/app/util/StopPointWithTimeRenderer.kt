package com.example.app.util

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.app.R
import com.example.app.data.model.BusWithTimeResponse
import com.example.app.domain.model.LineAndBus
import com.example.app.domain.model.StopPoint
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer

class StopPointWithTimeRenderer(
    private val context: Context,
    map: GoogleMap,
    clusterManager: ClusterManager<BusWithTimeResponse>
) : DefaultClusterRenderer<BusWithTimeResponse>(context, map, clusterManager) {

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
        item: BusWithTimeResponse,
        markerOptions: MarkerOptions
    ) {
        markerOptions
            .title(item.title)
            .position(LatLng(item.lat ?: 0.0, item.lng ?: 0.0))
            .icon(iconBusBitmap)
    }

    override fun onClusterItemRendered(clusterItem: BusWithTimeResponse, marker: Marker) {
        marker.tag = clusterItem
    }
}