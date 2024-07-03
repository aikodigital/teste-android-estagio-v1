package com.example.app.ui.bus

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.example.app.R
import com.example.app.domain.model.LineAndBus
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class MarkerInfoAdapter(private val context: Context) : GoogleMap.InfoWindowAdapter {
    override fun getInfoContents(marker: Marker): View? {
        val lineAndBus = marker.tag as? LineAndBus ?: return null

        val view = LayoutInflater.from(context).inflate(
            R.layout.custom_marker_info, null
        )

        view.findViewById<TextView>(R.id.tv_origin).text = lineAndBus.lineOrigin
        view.findViewById<TextView>(R.id.tv_destination).text = lineAndBus.lineDestination
        view.findViewById<TextView>(R.id.tv_bus_prefix).text = lineAndBus.busPrefix.toString()
        view.findViewById<TextView>(R.id.tv_line_code).text = lineAndBus.lineCode.toString()
        view.findViewById<TextView>(R.id.tv_is_accessible).text =
            if (lineAndBus.isAccessible) "Sim" else "Não"
        view.findViewById<TextView>(R.id.tv_request_hour).text = "Atualizado às ${lineAndBus.requestHour}"

        return view
    }

    override fun getInfoWindow(marker: Marker): View? {
        return null
    }
}