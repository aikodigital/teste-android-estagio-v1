package com.example.app.ui.lineBus

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.example.app.R
import com.example.app.domain.model.Bus
import com.example.app.domain.model.LineAndBus
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class MarkerBusAdapter(private val context: Context) : GoogleMap.InfoWindowAdapter {
    override fun getInfoContents(marker: Marker): View? {
        val bus = marker.tag as? Bus ?: return null

        val view = LayoutInflater.from(context).inflate(
            R.layout.custom_marker_bus, null
        )

        view.findViewById<TextView>(R.id.tv_bus_prefix).text = bus.busPrefix.toString()
        view.findViewById<TextView>(R.id.tv_is_accessible).text =
            if (bus.isAccessible) "Sim" else "Não"

        return view
    }

    override fun getInfoWindow(marker: Marker): View? {
        return null
    }
}