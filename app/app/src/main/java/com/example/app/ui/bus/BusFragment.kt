package com.example.app.ui.bus

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.app.R
import com.example.app.databinding.FragmentBusBinding
import com.example.app.domain.model.AllLines
import com.example.app.domain.model.LineAndBus
import com.example.app.domain.model.StopPoint
import com.example.app.ui.interfaces.ClusterRenderer
import com.example.app.util.ClusterManagerHelper
import com.example.app.util.LineAndBusRenderer
import com.example.app.util.StateView
import com.example.app.util.StopPointRenderer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
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
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnRefresh.setOnClickListener {
            getLines()
        }
    }

    class LineAndBusRendererFactory : ClusterRenderer<LineAndBus> {
        override fun createRenderer(
            context: Context,
            googleMap: GoogleMap,
            clusterManager: ClusterManager<LineAndBus>
        ): DefaultClusterRenderer<LineAndBus> {
            return LineAndBusRenderer(context, googleMap, clusterManager)
        }
    }

    private fun getLines() {
        viewModel.getLines().observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is StateView.Success -> {
                    binding.progressBar.visibility = View.GONE

                    stateView.data?.let { list ->
                        val busList = extractBusList(list)

                        val mapFragment =
                            childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
                        mapFragment.getMapAsync { googleMap ->
                            ClusterManagerHelper.setupClusterManager(
                                requireContext(),
                                googleMap,
                                busList,
                                LineAndBusRendererFactory()
                            ) { context -> MarkerInfoAdapter(context) }

                            googleMap.setOnMapLoadedCallback {
                                val bounds = LatLngBounds.builder()
                                busList.forEach { bounds.include(it.latLng) }
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

    private fun extractBusList(allLines: List<AllLines>): List<LineAndBus> {
        val lineAndBusList = mutableListOf<LineAndBus>()

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

                    lineAndBusList.add(busWithLine)
                }
            }
        }
        return lineAndBusList
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}