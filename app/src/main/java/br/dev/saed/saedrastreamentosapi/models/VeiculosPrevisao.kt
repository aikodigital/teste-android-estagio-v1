package br.dev.saed.saedrastreamentosapi.models

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

data class VeiculosPrevisao(
    val a: Boolean,
    val p: String,
    val px: Double,
    val py: Double,
    val t: String,
    val ta: String
) : ClusterItem {
    override fun getPosition(): LatLng {
        return LatLng(py, px)
    }

    override fun getTitle(): String {
        return p
    }

    override fun getSnippet(): String {
        return ""
    }

    override fun getZIndex(): Float? {
        return null
    }
}