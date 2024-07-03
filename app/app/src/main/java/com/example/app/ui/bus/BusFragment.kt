package com.example.app.ui.bus

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.app.R
import com.example.app.databinding.FragmentBusBinding
import com.example.app.domain.model.AllLines
import com.example.app.domain.model.LineAndBus
import com.example.app.util.BusRenderer
import com.example.app.util.StateView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.clustering.ClusterManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BusFragment : Fragment() {
    private var _binding: FragmentBusBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BusViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBusBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getLines()
    }

    private fun getLines() {
        viewModel.getLines().observe(viewLifecycleOwner, Observer { stateView ->
            when (stateView) {
                is StateView.Loading -> {
                }

                is StateView.Success -> {
                    stateView.data?.let { list ->
                        val busList = extractBusList(list)

                        val mapFragment =
                            childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
                        mapFragment.getMapAsync { googleMap ->
                            addClusteredMarkers(googleMap, busList)

                            googleMap.setOnMapLoadedCallback {
                                val bounds = LatLngBounds.builder()
                                busList.forEach { bounds.include(it.latLng) }
                                googleMap.moveCamera(
                                    CameraUpdateFactory.newLatLngBounds(
                                        bounds.build(),
                                        20
                                    )
                                )
                            }
                        }
                    }
                }

                is StateView.Error -> {
                    Toast.makeText(
                        requireContext(),
                        "Erro ao obter dados. Tente novamente mais tarde.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    private fun extractBusList(allLines: List<AllLines>): List<LineAndBus> {
        val lineAndBus = mutableListOf<LineAndBus>()

        allLines.forEach { allLines ->
            allLines.linesRelation.forEach { line ->
                line?.busList?.forEach { bus ->
                    val latLng = LatLng(bus?.lat ?: 0.0, bus?.lng ?: 0.0)

                    val busWithLine = LineAndBus(
                        line.fullPlacard,
                        bus?.busPrefix ?: 0,
                        line.lineOrigin,
                        line.lineDestination,
                        bus?.isAccessible ?: false,
                        allLines.requestHour,
                        latLng,
                        line.lineCode
                    )

                    lineAndBus.add(busWithLine)
                }
            }
        }
        return lineAndBus
    }

    private fun addClusteredMarkers(googleMap: GoogleMap, vehicles: List<LineAndBus>) {
        val clusterManager = ClusterManager<LineAndBus>(requireContext(), googleMap)

        clusterManager.clearItems()

        clusterManager.renderer =
            BusRenderer(
                requireContext(),
                googleMap,
                clusterManager
            )

        clusterManager.addItems(vehicles)
        clusterManager.cluster()

        googleMap.setOnCameraIdleListener {
            clusterManager.onCameraIdle()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}