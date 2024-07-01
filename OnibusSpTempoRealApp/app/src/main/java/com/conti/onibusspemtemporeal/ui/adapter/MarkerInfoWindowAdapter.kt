package com.conti.onibusspemtemporeal.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.conti.onibusspemtemporeal.R
import com.conti.onibusspemtemporeal.data.models.BusWithLine
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker


class MarkerInfoWindowAdapter(
    private val context: Context
) : GoogleMap.InfoWindowAdapter {

    override fun getInfoContents(p0: Marker): View? {

        val bus = p0.tag as? BusWithLine ?: return null

        val view = LayoutInflater.from(context).inflate(
            R.layout.marker_info_contents, null
        )
        
        view.findViewById<TextView>(
            R.id.textView_placard_full_from_api
        ).text = bus.fullPlacard
        view.findViewById<TextView>(
            R.id.textView_bus_number_from_api
        ).text = bus.prefixBus.toString()
        view.findViewById<TextView>(
            R.id.textView_origin_from_api
        ).text = bus.origin
        view.findViewById<TextView>(
            R.id.textView_destiny_from_api
        ).text = bus.destiny
        view.findViewById<TextView>(
            R.id.textView_acessible_from_api
        ).text = if (bus.accessibleBus) {
            "Sim"
        } else {
            "Não"
        }
        view.findViewById<TextView>(
            R.id.textView_last_hour_update_from_api
        ).text = bus.lastUpdate

        return view
    }

    override fun getInfoWindow(p0: Marker): View? {
        return null
    }

}