package br.com.daniel.aikoandroidestagio.ui.maps

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import br.com.daniel.aikoandroidestagio.R
import br.com.daniel.aikoandroidestagio.adapter.MarkerOnibusInfoAdapter
import br.com.daniel.aikoandroidestagio.adapter.MarkerInfoParadaAdapter

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import br.com.daniel.aikoandroidestagio.databinding.ActivityMapsBinding
import br.com.daniel.aikoandroidestagio.enums.From
import br.com.daniel.aikoandroidestagio.model.LocalizacaoVeiculos
import br.com.daniel.aikoandroidestagio.model.Parada
import br.com.daniel.aikoandroidestagio.model.VeiculoTag
import br.com.daniel.aikoandroidestagio.ui.PrevisaoChegada
import br.com.daniel.aikoandroidestagio.util.Constants
import br.com.daniel.aikoandroidestagio.util.Utils
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.*
import java.io.Serializable

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private var locationPermissionGranted = true
    private var lastKnownLocation: Location? = null
    private var cameraPosition: CameraPosition? = null
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var from = From.ERRO
    private val defaultLocation = LatLng(-23.5454758, -46.6455341)
    private val TAG = "DEBUG"
    private var onibusVermelhoBitmap: BitmapDescriptor? = null
    private var paradaVermelhaBitmap: BitmapDescriptor? = null
    private var paradas: List<Parada> = listOf()

    var linhasEonibus: LocalizacaoVeiculos? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configuraMapaElocalizador()
        identificaFragmentQueAcessou()
        configuraTitleEpegaExtra()
        getLocationPermission()

        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION)
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION)
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        updateLocationUI()
        getDeviceLocation()
        insereMarcadores()
    }

    private fun insereMarcadores() {
        when (from) {
            From.PARADAS -> {
                mMap.setInfoWindowAdapter(MarkerInfoParadaAdapter(this))
                paradaVermelhaBitmap =
                    Utils.bitmapDescriptorFromVector(this, R.drawable.ic_parada_vermelha)
                colocaMarcadorParadasAtualizaCamera()
            }
            From.ONIBUS -> {
                mMap.setInfoWindowAdapter(MarkerOnibusInfoAdapter(this))
                onibusVermelhoBitmap =
                    Utils.bitmapDescriptorFromVector(this, R.drawable.ic_onibus_vermelho)
                colocaMarcadoresOnibus()
            }
            else -> {
                Toast.makeText(this, getString(R.string.algo_errado), Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }

    private fun identificaFragmentQueAcessou() {
        from = intent.getSerializableExtra(Constants.from) as From
        Log.i(TAG, "Recebeu fragment: $from")
    }

    private fun configuraTitleEpegaExtra() {
        when (from) {
            From.PARADAS -> {
                title = getString(R.string.title_maps_paradas)
                paradas = intent.getSerializableExtra(Constants.parada) as List<Parada>
                Log.i(TAG, "Recebeu pelo extra: $paradas")
            }
            From.ONIBUS -> {
                title = getString(R.string.title_maps_onibus)
                linhasEonibus = intent.getSerializableExtra(Constants.veic) as LocalizacaoVeiculos?
            }
            else -> {
                Toast.makeText(this, getString(R.string.algo_errado), Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }

    private fun configuraMapaElocalizador() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
    }

    private fun getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        locationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    locationPermissionGranted = true
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
        updateLocationUI()
    }

    private fun colocaMarcadorParadasAtualizaCamera() {
        val bounds = LatLngBounds.builder()

        paradas.forEach { parada ->
            val pos = LatLng(parada.latitude, parada.longitude)
            val titulo = parada.nome
            val marker = mMap.addMarker(
                MarkerOptions()
                    .position(pos)
                    .icon(paradaVermelhaBitmap)
                    .title(titulo)
            )
            marker?.tag = parada
            bounds.include(pos)
            mMap.setOnInfoWindowClickListener {
                val intent = Intent(this, PrevisaoChegada::class.java).apply {
                    putExtra(Constants.parada, it.tag as Serializable)
                }
                startActivity(intent)
            }
        }
        mMap.setOnMapLoadedCallback {
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 250))
        }
    }

    private fun colocaMarcadoresOnibus() {
        linhasEonibus?.l?.forEach { linha ->
            linha.vs.forEach { veiculo ->
                val pos = LatLng(veiculo.py, veiculo.px)
                val titulo = veiculo.p.toString()
                val marker = mMap.addMarker(
                    MarkerOptions()
                        .position(pos)
                        .icon(onibusVermelhoBitmap)
                        .title(titulo)
                )
                marker?.tag = VeiculoTag(veiculo, linha)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun updateLocationUI() {
        if (mMap == null) {
            return
        }
        try {
            if (locationPermissionGranted) {
                mMap.isMyLocationEnabled = true
                mMap.uiSettings.isMyLocationButtonEnabled = true
            } else {
                mMap.isMyLocationEnabled = false
                mMap.uiSettings.isMyLocationButtonEnabled = false
                lastKnownLocation = null
                getLocationPermission()
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    @SuppressLint("MissingPermission")
    private fun getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (locationPermissionGranted) {
                val locationResult = fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Set the map's camera position to the current location of the device.
                        lastKnownLocation = task.result
                        if (lastKnownLocation != null) {
                            mMap.moveCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    LatLng(
                                        lastKnownLocation!!.latitude,
                                        lastKnownLocation!!.longitude
                                    ), DEFAULT_ZOOM.toFloat()
                                )
                            )
                        }
                    } else {
                        Log.i(TAG, "localizacao: $lastKnownLocation")
                        Log.d(TAG, "Current location is null. Using defaults.")
                        Log.e(TAG, "Exception: %s", task.exception)
                        mMap.moveCamera(
                            CameraUpdateFactory
                                .newLatLngZoom(defaultLocation, DEFAULT_ZOOM.toFloat())
                        )
                        mMap.uiSettings.isMyLocationButtonEnabled = false
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    //Salva a ultima localização do usuario
    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        mMap.let { map ->
            outState.putParcelable(KEY_CAMERA_POSITION, map.cameraPosition)
            outState.putParcelable(KEY_LOCATION, lastKnownLocation)
        }
        super.onSaveInstanceState(outState, outPersistentState)
    }

    companion object {
        private const val DEFAULT_ZOOM = 15
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
        private const val KEY_CAMERA_POSITION = "camera_position"
        private const val KEY_LOCATION = "location"
    }
}