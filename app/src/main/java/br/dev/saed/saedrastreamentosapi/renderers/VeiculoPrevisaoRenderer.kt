package br.dev.saed.saedrastreamentosapi.renderers

import android.content.Context
import androidx.core.content.ContextCompat
import br.dev.saed.saedrastreamentosapi.R
import br.dev.saed.saedrastreamentosapi.models.VeiculosPrevisao
import br.dev.saed.saedrastreamentosapi.utils.BitmapHelper
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer

class VeiculoPrevisaoRenderer(
    private val context: Context,
    map: GoogleMap,
    clusterManager: ClusterManager<VeiculosPrevisao>
) : DefaultClusterRenderer<VeiculosPrevisao>(
    context, map, clusterManager
) {

    private val onibusIcon: BitmapDescriptor by lazy {
        val color = ContextCompat.getColor(context, R.color.primary)
        BitmapHelper.vectorToBitmap(context, R.drawable.baseline_directions_bus_64, color)
    }

    override fun onBeforeClusterItemRendered(
        item: VeiculosPrevisao, markerOptions: MarkerOptions
    ) {
        markerOptions.title(item.title).position(item.position).icon(onibusIcon)
    }

    override fun onClusterItemRendered(clusterItem: VeiculosPrevisao, marker: Marker) {
        marker.tag = clusterItem
    }
}