package com.example.app.util

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.app.R
import com.example.app.domain.model.LineAndBus
import com.example.app.domain.model.StopPoint
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer

class StopPointRenderer(
    private val context: Context,
    map: GoogleMap,
    clusterManager: ClusterManager<StopPoint>
) : DefaultClusterRenderer<StopPoint>(context, map, clusterManager) {

    private val iconBusBitmap: BitmapDescriptor by lazy {
        val color = ContextCompat.getColor(
            context,
            R.color.icon_color
        )
        BitmapHelper.vectorToBitmap(
            context,
            R.drawable.ic_stop_point,
            color
        )
    }

    override fun onBeforeClusterItemRendered(
        item: StopPoint,
        markerOptions: MarkerOptions
    ) {
        markerOptions
            .title(item.title)
            .position(LatLng(item.lat, item.lng))
            .icon(iconBusBitmap)
    }

    override fun onClusterItemRendered(clusterItem: StopPoint, marker: Marker) {
        marker.tag = clusterItem
    }
}