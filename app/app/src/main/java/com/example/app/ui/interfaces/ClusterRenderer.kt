package com.example.app.ui.interfaces

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer

interface ClusterRenderer<T : ClusterItem> {
    fun createRenderer(
        context: Context,
        googleMap: GoogleMap,
        clusterManager: ClusterManager<T>
    ): DefaultClusterRenderer<T>
}
