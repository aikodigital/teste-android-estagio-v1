package com.example.app.ui.lineBus

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.app.R
import com.example.app.databinding.FragmentLineBusBinding
import com.example.app.domain.model.Bus
import com.example.app.domain.model.Line
import com.example.app.ui.bus.MarkerInfoAdapter
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
class LineBusFragment : Fragment() {

    private var _binding: FragmentLineBusBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LineBusViewModel by viewModels()
    private val args: LineBusFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLineBusBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getLineByCode()
    }

    private fun getLineByCode() {
        viewModel.getLineByCode(args.lineCode.toString()).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is StateView.Success -> {
                    binding.progressBar.visibility = View.GONE

                    stateView.data?.let { list ->
                        val busList = extractBusList(list[0])

                        val mapFragment =
                            childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
                        mapFragment.getMapAsync { googleMap ->
                            addClusteredMarkers(googleMap, busList)

                            googleMap.setOnMapLoadedCallback {
                                val bounds = LatLngBounds.builder()
                                busList.forEach { bounds.include(LatLng(it.lat, it.lng)) }
                                googleMap.moveCamera(
                                    CameraUpdateFactory.newLatLngBounds(
                                        bounds.build(),
                                        50
                                    )
                                )
                            }
                        }
                    }
                }

                is StateView.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "Erro ao obter dados. Tente novamente mais tarde.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    @SuppressLint("PotentialBehaviorOverride")
    private fun addClusteredMarkers(googleMap: GoogleMap, busList: List<Bus>) {
        val clusterManager = ClusterManager<Bus>(requireContext(), googleMap)

        clusterManager.clearItems()

        clusterManager.renderer =
            BusRenderer(
                requireContext(),
                googleMap,
                clusterManager
            )

        clusterManager.markerCollection.setInfoWindowAdapter(MarkerBusAdapter(requireContext()))

        clusterManager.addItems(busList)
        clusterManager.cluster()


        googleMap.setOnCameraIdleListener {
            clusterManager.onCameraIdle()
        }
    }

    private fun extractBusList(line: Line): List<Bus> {
        val busList = mutableListOf<Bus>()

        line.busList.forEach { bus ->
            val latLng = LatLng(bus?.lat ?: 0.0, bus?.lng ?: 0.0)

            val searchBus = Bus(
                bus?.busPrefix ?: 0,
                bus?.isAccessible ?: false,
                bus?.utcRequestHour ?: "",
                bus?.lat ?: 0.0,
                bus?.lng ?: 0.0,
                bus?.sv ?: false,
                bus?.`is` ?: false,
            )

            busList.add(searchBus)
        }
        return busList
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}