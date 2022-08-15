package br.com.aj.message.appaiko.util

import br.com.aj.message.appaiko.data.MapData
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


/**
 * Renderiza de acordo onde markes está , para evitar vazamento de memoria e sobre carregar a CPU
*/
class ProjectiomItemMap(val map: GoogleMap) :
    GoogleMap.OnCameraMoveListener {

    private val mapMarkers = HashMap<String,Marker>()

    //PARADAS
    val items = HashMap<String, MarkerOptions>()

    //ONIBUS
    val items2 = HashMap<String, MarkerOptions>()

    fun show(){
        val bounds = map.projection.visibleRegion.latLngBounds;
        val size = map.cameraPosition.zoom





        for (item in items) {
            if (size >= 15f && bounds.contains(
                    LatLng(
                        item.value.position.latitude,
                        item.value.position.longitude
                    )
                )
            ) {
                if (!mapMarkers.containsKey(item.key)) {
                    val marker = map.addMarker(item.value)
                    marker?.tag = item.key
                    if (marker != null) {
                        mapMarkers[item.key] = marker
                    }
                }
            } else {
                if (mapMarkers.containsKey(item.key)) {
                    mapMarkers[item.key]?.remove()
                    mapMarkers.remove(item.key)
                }
            }

        }
        for (item in items2) {
            if (size >= 15f && bounds.contains(
                    LatLng(
                        item.value.position.latitude,
                        item.value.position.longitude
                    )
                )
            ) {


                if (!mapMarkers.containsKey(item.key)) {

                    val marker = map.addMarker(item.value)
                    marker?.tag = item.key
                    if (marker != null) {
                        mapMarkers[item.key] = marker
                    }


                }

            } else {
                if (mapMarkers.containsKey(item.key)) {
                    mapMarkers[item.key]?.remove()
                    mapMarkers.remove(item.key)
                }
            }

        }

    }

    override fun onCameraMove() {
        show()
    }


}
