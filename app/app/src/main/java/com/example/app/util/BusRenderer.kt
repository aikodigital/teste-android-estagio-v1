package com.example.app.util

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.app.R
import com.example.app.domain.model.LineAndBus
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer

class BusRenderer(
    private val context: Context,
    map: GoogleMap,
    clusterManager: ClusterManager<LineAndBus>
) : DefaultClusterRenderer<LineAndBus>(context, map, clusterManager) {

    private val iconBusBitmap: BitmapDescriptor by lazy {
        val color = ContextCompat.getColor(
            context,
            R.color.black
        )
        BitmapHelper.vectorToBitmap(
            context,
            R.drawable.ic_bus,
            color
        )
    }

    override fun onBeforeClusterItemRendered(
        item: LineAndBus,
        markerOptions: MarkerOptions
    ) {
        markerOptions.title(item.fullPlacard)
            .position(item.latLng)
            .icon(iconBusBitmap)
    }

    override fun onClusterItemRendered(clusterItem: LineAndBus, marker: Marker) {
        marker.tag = clusterItem
    }
}