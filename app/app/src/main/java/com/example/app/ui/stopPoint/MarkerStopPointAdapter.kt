package com.example.app.ui.stopPoint

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.example.app.R
import com.example.app.domain.model.StopPoint
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class MarkerStopPointAdapter(private val context: Context) : GoogleMap.InfoWindowAdapter {
    override fun getInfoContents(marker: Marker): View? {
        val stopPoint = marker.tag as? StopPoint ?: return null

        val view = LayoutInflater.from(context).inflate(
            R.layout.custom_marker_stop_point, null
        )

        view.findViewById<TextView>(R.id.tv_stop_point_address).text = stopPoint.stopPointAddress
        view.findViewById<TextView>(R.id.tv_stop_point_name).text = stopPoint.stopPointName
        view.findViewById<TextView>(R.id.tv_stop_point_code).text = stopPoint.stopPointCode.toString()

        return view
    }

    override fun getInfoWindow(marker: Marker): View? {
        return null
    }
}