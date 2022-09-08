package com.andreesperanca.deolhonobus.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.andreesperanca.deolhonobus.R
import com.andreesperanca.deolhonobus.models.Place
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class MarkerInfoAdapter(private val context: Context): GoogleMap.InfoWindowAdapter {

    override fun getInfoContents(p0: Marker): View? {
        val place = p0.tag as? Place ?: return null
        val view = LayoutInflater.from(context).inflate(R.layout.custom_marker_info, null)

        val txtTitle = view.findViewById<TextView>(R.id.txt_title)

        txtTitle.text = place.title

        return view
    }

    override fun getInfoWindow(p0: Marker): View? = null
}