package com.conti.onibusspemtemporeal.util

import android.content.Context
import androidx.core.content.ContextCompat
import com.conti.onibusspemtemporeal.R
import com.conti.onibusspemtemporeal.data.models.BusWithLine
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer

class BusRenderer(
    private val context: Context,
    map: GoogleMap,
    clusterManager: ClusterManager<BusWithLine>
) : DefaultClusterRenderer<BusWithLine>(context, map, clusterManager) {


    private val iconBusBitmap: BitmapDescriptor by lazy {
        val color = ContextCompat.getColor(
            context,
            R.color.black
        )
        BitmapHelper.vectorToBitmap(
            context,
            R.drawable.ic_bus_card,
            color
        )
    }


    override fun onBeforeClusterItemRendered(
        item: BusWithLine,
        markerOptions: MarkerOptions
    ) {
        markerOptions
            .position(item.latLng)
            .icon(iconBusBitmap)
    }


    override fun onClusterItemRendered(clusterItem: BusWithLine, marker: Marker) {
        marker.tag = clusterItem
    }
}