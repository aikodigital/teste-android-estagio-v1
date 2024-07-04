package com.example.app.ui.stopPoint

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.app.R
import com.example.app.databinding.FragmentStopPointBinding
import com.example.app.domain.model.StopPoint
import com.example.app.ui.interfaces.ClusterRenderer
import com.example.app.util.ClusterManagerHelper
import com.example.app.util.StateView
import com.example.app.util.StopPointRenderer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StopPointFragment : Fragment() {
    private var _binding: FragmentStopPointBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StopPointViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStopPointBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getStopPoints()
    }

    class StopPointRendererFactory : ClusterRenderer<StopPoint> {
        override fun createRenderer(
            context: Context,
            googleMap: GoogleMap,
            clusterManager: ClusterManager<StopPoint>
        ): DefaultClusterRenderer<StopPoint> {
            return StopPointRenderer(context, googleMap, clusterManager)
        }
    }

    private fun getStopPoints() {
        viewModel.getStopPointByLine().observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is StateView.Success -> {
                    binding.progressBar.visibility = View.GONE

                    stateView.data?.let { list ->
                        val mapFragment =
                            childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
                        mapFragment.getMapAsync { googleMap ->
                            ClusterManagerHelper.setupClusterManager(
                                requireContext(),
                                googleMap,
                                list[0],
                                StopPointRendererFactory()
                            ) { context -> MarkerStopPointAdapter(context) }

                            googleMap.setOnMapLoadedCallback {
                                val bounds = LatLngBounds.builder()
                                list[0].forEach { bounds.include(LatLng(it.lat, it.lng)) }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}