package com.example.apiolhovivo

import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.Checkable
import androidx.annotation.Px
import androidx.lifecycle.lifecycleScope
import classes.Posicao
import classes.getLinhas
import classes.getParadas
import classes.getPosicao
import classes.validation

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.apiolhovivo.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import kotlinx.coroutines.launch

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
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val SaoPaulo = LatLngBounds(
            LatLng(-24.0087, -46.8260),
            LatLng(-23.2115, -46.3653)
        )

        mMap.setLatLngBoundsForCameraTarget(SaoPaulo)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SaoPaulo.center, 10f))

        val btnRefresh: Button = findViewById(R.id.btn_refresh)
        val chbParadas: CheckBox = findViewById(R.id.chb_Paradas)
        val chbOnibus: CheckBox = findViewById(R.id.chb_Onibus)

        val codigoLinha = intent.getStringExtra("codigoLinha").toString()

        lifecycleScope.launch {
            try {
                val validation = validation()

                println(validation)
            } catch (e: Exception) {
                println(e)
            }
            loadMarkers(mMap, codigoLinha, chbParadas.isChecked, chbOnibus.isChecked)
        }


        btnRefresh.setOnClickListener()
        {
            lifecycleScope.launch {
                loadMarkers(mMap, codigoLinha, chbParadas.isChecked, chbOnibus.isChecked)
            }
        }


        mMap.setOnMarkerClickListener { marker: Marker ->
            val tag = marker.tag.toString()
            val info: List<String> = tag.split(";")

            if (tag != "null;null") {
                val intent = Intent(this, InfoActivity::class.java)
                intent.putExtra("codigoParada", info[0])
                intent.putExtra("codigoLinha", codigoLinha)
                intent.putExtra("nomeParada", marker.title)
                intent.putExtra("enderecoParada", info[1])
                startActivity(intent)
            }

            println(marker.tag)
            false
        }
    }

    suspend fun loadMarkers(
        map: GoogleMap,
        codigoLinha: String,
        paradas: Boolean,
        onibus: Boolean
    ) {
        map.clear()

        try {
            if (paradas) {
                val parada = getParadas(codigoLinha)

                for (i in parada) createMarker(
                    map,
                    i.py,
                    i.px,
                    i.np,
                    R.drawable.estacao,
                    100,
                    134,
                    i.cp.toString(),
                    i.ed
                )
            }


            if (onibus) {
                val posicao = getPosicao(codigoLinha)

                for (i in posicao.vs) createMarker(
                    map,
                    i.py,
                    i.px,
                    i.p.toString(),
                    R.drawable.onibus,
                    100,
                    99,
                    null.toString(),
                    null.toString()
                )
            }

        } catch (e: Exception) {
            Log.e("Erro", e.toString())
        }
    }


    fun createMarker(
        map: GoogleMap,
        py: Double,
        px: Double,
        p: String,
        image: Int,
        width: Int,
        height: Int,
        codigo: String,
        endereco: String
    ) {
        val pos = LatLng(py, px)
        var marker = map.addMarker(
            MarkerOptions()
                .position(pos)
                .title(p)
                .icon(
                    BitmapDescriptorFactory.fromBitmap(
                        Bitmap.createScaledBitmap(
                            BitmapFactory.decodeResource(resources, image),
                            width,
                            height,
                            false
                        )
                    )
                )
        )

        if (marker != null) marker.setTag("$codigo;$endereco")
    }


}