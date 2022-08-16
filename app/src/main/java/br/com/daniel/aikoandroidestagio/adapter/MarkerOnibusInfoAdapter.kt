package br.com.daniel.aikoandroidestagio.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import br.com.daniel.aikoandroidestagio.R
import br.com.daniel.aikoandroidestagio.model.VeiculoTag
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class MarkerOnibusInfoAdapter(private val context: Context) : GoogleMap.InfoWindowAdapter {
    override fun getInfoContents(marker: Marker): View? = null

    override fun getInfoWindow(marker: Marker): View? {
        val veiculoLinha = marker.tag as? VeiculoTag ?: return null
        val view = LayoutInflater.from(context).inflate(R.layout.custom_marker_info, null)

        val dest = "Destino: ${veiculoLinha.linha.lt1}"
        val orig = "Origem: ${veiculoLinha.linha.lt0}"

        view.findViewById<TextView>(R.id.info_numero).text = veiculoLinha.veiculo.p.toString()
        view.findViewById<TextView>(R.id.info_secundaria).text = dest
        view.findViewById<TextView>(R.id.info_suporte).text = orig

        return view
    }
}