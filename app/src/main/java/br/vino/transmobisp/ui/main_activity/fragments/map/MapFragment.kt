package br.vino.transmobisp.ui.main_activity.fragments.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import br.vino.transmobisp.R
import br.vino.transmobisp.databinding.FragmentMapBinding
import br.vino.transmobisp.model.Stop
import br.vino.transmobisp.model.VehicleLine
import br.vino.transmobisp.ui.component.BitmapHelper
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar

class MapFragment : Fragment(){

    private var _binding: FragmentMapBinding? = null
    private val viewModel  by viewModels<MapViewModel>()
    private lateinit var googleMap: GoogleMap


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // toolbar configuration
        setHasOptionsMenu(true)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync {gMap ->
            googleMap = gMap

            googleMap.setOnMapLoadedCallback {
                val posicao = LatLng(-23.5868031,-46.6843406)
                setMapView(googleMap, posicao, 500.0)
                viewModel.fetchVehicles()
            }

        }
        setupListeners()

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_map, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_refresh -> {
                viewModel.fetchVehicles() // Chama o método de atualização dos veículos
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupListeners(){
        viewModel.vehiclesLine.observe(viewLifecycleOwner) { vehiclesLineList ->
            if(checkZoomLevel()){
                updateVehicleMarkers(vehiclesLineList)
            }else{
                Snackbar.make(binding.root, "Reduza o zoom para atualizar os veiculos", Snackbar.LENGTH_LONG).show()
            }
        }
        viewModel.stops.observe(viewLifecycleOwner){ stopList ->
            updateStopsMarkers(stopList)
        }
    }

    private fun updateVehicleMarkers(vehiclesLineList: List<VehicleLine>) {
        googleMap.clear()  // Clear existing markers

        val bounds = googleMap.projection.visibleRegion.latLngBounds
        val visibleVehiclesLines = vehiclesLineList.filter { vehiclesLine ->
            vehiclesLine.vs.any{ vehicle ->
                bounds.contains(LatLng(vehicle.py, vehicle.px))
            }
        }

        visibleVehiclesLines.forEach { vehicleLine ->
            viewModel.getStops(vehicleLine.cl.toString())
            vehicleLine.vs.forEach { vehicle ->
                val position = LatLng(vehicle.py, vehicle.px)
                googleMap.addMarker(
                    MarkerOptions()
                        .title("Linha ${vehicleLine.c}")
                        .position(position)
                        .icon(
                            BitmapHelper.vectorToBitmap(
                                requireContext(),
                                R.drawable.bus_icon,
                                ContextCompat.getColor(requireContext(), R.color.red)
                            )
                        )
                )
            }
        }
    }

    private fun updateStopsMarkers(stopsList : List<Stop>){
        val bounds = googleMap.projection.visibleRegion.latLngBounds
        val visibleStops = stopsList.filter { stop ->
            bounds.contains(LatLng(stop.py, stop.px))
        }

        visibleStops.forEach { stop ->
            val position = LatLng(stop.py, stop.px)
            googleMap.addMarker(
                MarkerOptions()
                    .title("${stop.np} - ${stop.cp}")
                    .position(position)
                    .icon(
                        BitmapHelper.vectorToBitmap(
                            requireContext(),
                            R.drawable.bus_stop_icon_24,
                            ContextCompat.getColor(requireContext(), R.color.green)
                        )
                    )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun calculateBounds(center: LatLng, distanceInMeters: Double): LatLngBounds {
        val distanceInDegrees = distanceInMeters / 111000f // 1 degree is approximately 111 km

        val southWest = LatLng(center.latitude - distanceInDegrees, center.longitude - distanceInDegrees)
        val northEast = LatLng(center.latitude + distanceInDegrees, center.longitude + distanceInDegrees)

        return LatLngBounds(southWest, northEast)
    }

    fun setMapView(googleMap: GoogleMap, center: LatLng, distanceInMeters: Double) {
        val bounds = calculateBounds(center, distanceInMeters)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 0))
    }

    fun checkZoomLevel() : Boolean{
        val visibleRegion = googleMap.projection.visibleRegion
        val bounds = visibleRegion.latLngBounds

        // Calcula a diagonal do retângulo da região visível
        val diagonalDistance = calculateDistance(bounds.southwest, bounds.northeast)

        // Converte para metros (aproximadamente, dependendo do nível de zoom)
        val diagonalDistanceMeters = diagonalDistance * 111000 // Aproximadamente 1 grau de latitude ~ 111 km

        // Define o limite máximo de 2 km²
        val maxArea = 400000 // Em metros quadrados

        return diagonalDistanceMeters <= maxArea.toDouble()
    }

    fun calculateDistance(p1: LatLng, p2: LatLng): Double {
        val radiusEarth = 6371 // raio da Terra em km
        val lat1 = Math.toRadians(p1.latitude)
        val lon1 = Math.toRadians(p1.longitude)
        val lat2 = Math.toRadians(p2.latitude)
        val lon2 = Math.toRadians(p2.longitude)

        val dLon = lon2 - lon1
        val dLat = lat2 - lat1

        val a = Math.pow(Math.sin(dLat / 2), 2.0) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dLon / 2), 2.0)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))

        return radiusEarth * c // Distância em km
    }

}

