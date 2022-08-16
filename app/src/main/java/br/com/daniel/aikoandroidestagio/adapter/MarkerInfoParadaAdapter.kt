package br.com.daniel.aikoandroidestagio.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import br.com.daniel.aikoandroidestagio.R
import br.com.daniel.aikoandroidestagio.model.Parada
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class MarkerInfoParadaAdapter(private val context: Context) : GoogleMap.InfoWindowAdapter {
    override fun getInfoContents(marker: Marker): View? = null

    override fun getInfoWindow(marker: Marker): View? {
        val parada = marker.tag as? Parada ?: return null
        val view = LayoutInflater.from(context).inflate(R.layout.custom_marker_info, null)

        view.findViewById<TextView>(R.id.info_numero).text = parada.nome
        view.findViewById<TextView>(R.id.info_secundaria).text = parada.enderecoParada
        view.findViewById<TextView>(R.id.info_suporte).text = context.getString(R.string.prev_chegada)

        return view
    }
}