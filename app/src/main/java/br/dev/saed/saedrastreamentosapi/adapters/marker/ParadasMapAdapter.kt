package br.dev.saed.saedrastreamentosapi.adapters.marker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import br.dev.saed.saedrastreamentosapi.databinding.MarcadorParadaBinding
import br.dev.saed.saedrastreamentosapi.models.Parada
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.model.Marker

class ParadasMapAdapter(private val context: Context) : InfoWindowAdapter {

    override fun getInfoContents(marker: Marker): View? {
        val parada = marker.tag as? Parada ?: return null
        val layoutInflater = LayoutInflater.from(context)
        val binding = MarcadorParadaBinding.inflate(layoutInflater, null, false)

        binding.textNomeParada.text = parada.np
        binding.textEnderecoParada.text = parada.ed
        binding.textCodigoParada.text = parada.cp.toString()

        return binding.root
    }

    override fun getInfoWindow(p0: Marker): View? {
        return null
    }
}