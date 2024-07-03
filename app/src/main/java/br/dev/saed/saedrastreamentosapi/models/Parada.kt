package br.dev.saed.saedrastreamentosapi.models

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class Parada(
    val cp: Int,
    val ed: String,
    val np: String,
    val px: Double,
    val py: Double
) : ClusterItem, Parcelable {
    override fun getPosition(): LatLng {
        return LatLng(py, px)
    }

    override fun getTitle(): String {
        return np
    }

    override fun getSnippet(): String {
        return ed
    }

    override fun getZIndex(): Float? {
        return null
    }
}