package com.example.teste.ui

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.teste.R
import com.example.teste.models.ArrivalPredictionResponse
import com.example.teste.models.LineArrival
import com.example.teste.models.VehicleArrival
import com.example.teste.models.LinePosition
import com.example.teste.models.Stop
import com.example.teste.models.VehiclePosition
import com.example.teste.network.OlhoVivoService
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var searchView: SearchView
    private var filterType: FilterType = FilterType.VEHICLE

    enum class FilterType {
        VEHICLE, STOP
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Mapa
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // SearchView
        searchView = findViewById(R.id.searchView)
        searchView.setOnClickListener {
            searchView.isIconified = false
        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    if (it.isNotEmpty()) {
                        searchAndDisplayBusLines(it)
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        // Filtro
        val filterButton = findViewById<ImageButton>(R.id.filterButton)
        filterButton.setOnClickListener {
            toggleFilterMenuVisibility()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val saoPauloLatLng = LatLng(-23.550520, -46.633308)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(saoPauloLatLng, 12f))

        mMap.setOnMarkerClickListener { marker ->
            val tag = marker.tag
            when (tag) {
                is Stop -> {
                    // Ao clicar em uma parada, exibir detalhes e previsão de chegada
                    fetchAndShowStopDetails(tag)
                    true
                }
                is Pair<*, *> -> {
                    val pair = tag as? Pair<*, *>
                    val vehiclePosition = pair?.first as? VehiclePosition
                    val linePosition = pair?.second as? LinePosition

                    if (vehiclePosition != null && linePosition != null) {
                        val bottomSheet = LineInfoBottomSheetFragment(vehiclePosition, linePosition)
                        bottomSheet.show(supportFragmentManager, bottomSheet.tag)
                        true
                    } else {
                        false
                    }
                }
                else -> false
            }
        }
    }

    private fun getMarkerIcon(type: FilterType): BitmapDescriptor {
        return when (type) {
            FilterType.VEHICLE -> vectorToBitmapDescriptor(R.drawable.ic_bus_marker_24)
            FilterType.STOP -> vectorToBitmapDescriptor(R.drawable.ic_bus_stop_24)
        }
    }

    private fun searchAndDisplayBusLines(searchTerm: String) {
        CoroutineScope(Dispatchers.IO).launch {
            if (OlhoVivoService.authCookie == null && !OlhoVivoService.authenticate()) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MapsActivity, "Falha na autenticação", Toast.LENGTH_SHORT).show()
                }
                return@launch
            }

            val busLines = OlhoVivoService.searchLines(searchTerm)

            if (busLines.isNullOrEmpty()) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MapsActivity, "Nenhuma linha encontrada", Toast.LENGTH_SHORT).show()
                }
                return@launch
            }

            val line = busLines.firstOrNull() ?: return@launch
            val lineId = line.cl

            when (filterType) {
                FilterType.VEHICLE -> {
                    val vehiclePositionsResponse = OlhoVivoService.getBusPositions()
                    if (vehiclePositionsResponse != null) {
                        withContext(Dispatchers.Main) {
                            mMap.clear()
                            vehiclePositionsResponse.l
                                .filter { it.cl == lineId }
                                .forEach { linePosition ->
                                    linePosition.vs.forEach { vehiclePosition ->
                                        val latLng = LatLng(vehiclePosition.py, vehiclePosition.px)
                                        val marker = mMap.addMarker(
                                            MarkerOptions()
                                                .position(latLng)
                                                .title("Veículo ${vehiclePosition.p}")
                                                .icon(getMarkerIcon(FilterType.VEHICLE))
                                        )
                                        marker?.tag = Pair(vehiclePosition, linePosition)
                                    }
                                }
                            vehiclePositionsResponse.l
                                .flatMap { it.vs }
                                .firstOrNull()?.let { firstVehicle ->
                                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(firstVehicle.py, firstVehicle.px), 12f))
                                }
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@MapsActivity, "Não foi possível obter posições dos veículos", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                FilterType.STOP -> {
                    val stops = OlhoVivoService.getStopsByLine(lineId)
                    if (stops != null) {
                        withContext(Dispatchers.Main) {
                            mMap.clear()
                            stops.forEach { stop ->
                                val latLng = LatLng(stop.py, stop.px)
                                val marker = mMap.addMarker(
                                    MarkerOptions()
                                        .position(latLng)
                                        .title("Parada: ${stop.np}")
                                        .snippet("Endereço: ${stop.ed}")
                                        .icon(getMarkerIcon(FilterType.STOP))
                                )
                                marker?.tag = stop
                            }
                            stops.firstOrNull()?.let { firstStop ->
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(firstStop.py, firstStop.px), 12f))
                            }
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@MapsActivity, "Não foi possível obter paradas", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun toggleFilterMenuVisibility() {
        val filterMenuContainer = findViewById<FrameLayout>(R.id.filterMenuContainer)
        if (filterMenuContainer.visibility == View.GONE) {
            filterMenuContainer.visibility = View.VISIBLE

            val filterGroup = findViewById<RadioGroup>(R.id.filterGroup)

            filterGroup.setOnCheckedChangeListener { _, checkedId ->
                filterType = when (checkedId) {
                    R.id.filterVehicles -> FilterType.VEHICLE
                    R.id.filterStops -> FilterType.STOP
                    else -> FilterType.VEHICLE
                }
                searchView.query?.let { query ->
                    if (query.isNotEmpty()) {
                        searchAndDisplayBusLines(query.toString())
                    }
                }
                filterMenuContainer.visibility = View.GONE
            }
        } else {
            filterMenuContainer.visibility = View.GONE
        }
    }

    private fun showStopDetailsDialog(stop: Stop, arrivalPredictionResponse: ArrivalPredictionResponse?) {

        Log.d("MapsActivity", "ArrivalPredictionResponse: $arrivalPredictionResponse")
        Log.d("API", "Resposta da API: $arrivalPredictionResponse")


        val message = buildString {
            append("Nome: ${stop.np}\n")
            append("Endereço: ${stop.ed}\n\n")

            if (arrivalPredictionResponse != null) {
                val lineArrivals: List<LineArrival> = arrivalPredictionResponse.l ?: emptyList()
                append("Previsão de chegada:\n")

                if (lineArrivals.isNotEmpty()) {
                    lineArrivals.forEach { lineArrival ->
                        append("Linha ${lineArrival.cl} - ${lineArrival.lt0} para ${lineArrival.lt1}\n")
                        val vehicleArrivals: List<VehicleArrival> = lineArrival.vs ?: emptyList()
                        if (vehicleArrivals.isNotEmpty()) {
                            vehicleArrivals.forEach { vehicleArrival ->
                                append("Veículo ${vehicleArrival.p}: ${vehicleArrival.t}\n")
                            }
                        } else {
                            append("Nenhuma previsão de chegada disponível para esta linha.\n")
                        }
                        append("\n")
                    }
                } else {
                    append("Nenhuma linha disponível para a parada selecionada.\n")
                }
            } else {
                append("Previsão de chegada não disponível.")
            }
        }

        Log.d("MapsActivity", "Dialog Message: $message")
        Log.d("API", "Line Arrivals: ${arrivalPredictionResponse?.l}")

        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle("Detalhes da Parada")
        dialogBuilder.setMessage(message)
        dialogBuilder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }

    private fun fetchAndShowStopDetails(stop: Stop) {
        CoroutineScope(Dispatchers.IO).launch {
            val busLines = OlhoVivoService.searchLines(searchView.query.toString()) // Recuperar linha baseada na pesquisa
            val lineId = busLines?.firstOrNull()?.cl // Pegue o primeiro código de linha disponível

            if (lineId != null) {
                val arrivalPredictionResponse = OlhoVivoService.getArrivalPrediction(stop.cp, lineId)
                withContext(Dispatchers.Main) {
                    showStopDetailsDialog(stop, arrivalPredictionResponse)
                }
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MapsActivity, "Linha não encontrada", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun vectorToBitmapDescriptor(vectorResId: Int): BitmapDescriptor {
        val vectorDrawable = ContextCompat.getDrawable(this, vectorResId)
        val bitmap = Bitmap.createBitmap(
            vectorDrawable?.intrinsicWidth ?: 0,
            vectorDrawable?.intrinsicHeight ?: 0,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable?.setBounds(0, 0, canvas.width, canvas.height)
        vectorDrawable?.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}
