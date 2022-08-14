package br.com.daniel.aikoandroidestagio.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import br.com.daniel.aikoandroidestagio.R
import br.com.daniel.aikoandroidestagio.model.V
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class MarkerInfoAdapter(private val context: Context) : GoogleMap.InfoWindowAdapter {
    override fun getInfoContents(marker: Marker): View? = null

    override fun getInfoWindow(marker: Marker): View? {
        val veiculo = marker.tag as? V ?: return null
        val view = LayoutInflater.from(context).inflate(R.layout.custom_marker_info, null)

        val dest = "Destino: ${veiculo.destino}"
        val orig = "Origem: ${veiculo.origem}"

        view.findViewById<TextView>(R.id.info_numero).text = veiculo.p.toString()
        view.findViewById<TextView>(R.id.info_secundaria).text = dest
        view.findViewById<TextView>(R.id.info_suporte).text = orig

        return view
    }
}