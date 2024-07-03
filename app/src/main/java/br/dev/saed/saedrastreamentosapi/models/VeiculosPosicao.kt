package br.dev.saed.saedrastreamentosapi.models

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

data class VeiculosPosicao(
    val a: Boolean,
    val p: Int,
    val px: Double,
    val py: Double,
    val ta: String,
) : ClusterItem {
    override fun getPosition(): LatLng {
        return LatLng(py, px)
    }

    override fun getTitle(): String {
        return p.toString()
    }

    override fun getSnippet(): String {
        return ta
    }

    override fun getZIndex(): Float? {
        return null
    }
}