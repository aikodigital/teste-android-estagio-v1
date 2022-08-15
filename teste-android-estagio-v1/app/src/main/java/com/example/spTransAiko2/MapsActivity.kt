package com.example.spTransAiko2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.spTransAiko2.R
import com.example.spTransAiko2.databinding.ActivityMapsBinding
import com.example.spTransAiko2.model.apiResponse
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions



class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        val apiResponse = apiResponse()
    }

    //TODO: iterar pela lista de posiçoes recebidas da api e ir adiconando marcadores
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Adiciona o marcador e move a camera
        val busSpot = LatLng(-23.00, -46.00)
        mMap.addMarker(MarkerOptions().position(busSpot).title("Info Marcador"))
        val cameraPosition = CameraPosition.Builder()
            .target(busSpot) // Centraliza a camera no busSpot
            .zoom(10f)            // Define o zoom
            .bearing(90f)         // define a orientaçao da camera em graus
            .build()
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(cameraPosition))
    }
}