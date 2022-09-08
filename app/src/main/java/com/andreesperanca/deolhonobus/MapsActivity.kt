package com.andreesperanca.deolhonobus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.andreesperanca.deolhonobus.adapters.MarkerInfoAdapter

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.andreesperanca.deolhonobus.databinding.ActivityMapsBinding
import com.andreesperanca.deolhonobus.models.MarkerInGmaps
import com.andreesperanca.deolhonobus.util.BitMapHelper
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private var args: MarkerInGmaps? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_DeOlhoNoBus)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent != null) {
            args = intent.getParcelableExtra<MarkerInGmaps>("markersForTheMap")
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        addMarkers(googleMap)
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style))
        googleMap.setInfoWindowAdapter(MarkerInfoAdapter(this))
        googleMap.setOnMapLoadedCallback {
            val bounds = LatLngBounds.Builder()
            args?.listMarker?.forEach {
                bounds.include(it.lng)
                googleMap.moveCamera(
                    CameraUpdateFactory.newLatLngBounds(
                        bounds.build(),
                        80
                    )
                )
            }
        }


//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    private fun addMarkers(googleMap: GoogleMap) {
        args?.listMarker?.forEach {
            val marker1 = googleMap.addMarker(
                MarkerOptions()
                    .title(args?.title)
                    .position(it.lng)
                    .icon(
                        BitMapHelper.vectorToBitMap(
                            this,
                            R.drawable.ic_location_24,
                            ContextCompat.getColor(this, R.color.dark_blue)
                        )
                    )
            )
            marker1?.tag = it
        }
    }
}

