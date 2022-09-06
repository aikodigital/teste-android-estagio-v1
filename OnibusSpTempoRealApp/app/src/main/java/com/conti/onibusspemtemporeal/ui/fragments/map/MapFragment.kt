package com.conti.onibusspemtemporeal.ui.fragments.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.conti.onibusspemtemporeal.R
import com.conti.onibusspemtemporeal.data.models.Bus
import com.conti.onibusspemtemporeal.data.models.BusWithLine
import com.conti.onibusspemtemporeal.databinding.FragmentMapBinding
import com.conti.onibusspemtemporeal.ui.adapter.MarkerInfoWindowAdapter
import com.conti.onibusspemtemporeal.ui.viewModel.OnibusSpViewModel
import com.conti.onibusspemtemporeal.util.BitmapHelper
import com.conti.onibusspemtemporeal.util.BusRenderer
import com.conti.onibusspemtemporeal.util.retrofitHandling.Resource
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MapFragment : Fragment() {

    private lateinit var binding: FragmentMapBinding
    private val viewModel: OnibusSpViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMapBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupMap()
    }


    private fun observerBus(clusterManager: ClusterManager<BusWithLine>) {
        lifecycleScope.launch {

            repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.uiState.collectLatest { uiState ->

                    when {
                        uiState.currentBuses.isNotEmpty() -> {
                            clusterManager.clearItems()
                            clusterManager.addItems(uiState.currentBuses)
                            clusterManager.cluster()
                        }

                        uiState.message.isNotEmpty() -> {
                            Toast.makeText(requireContext(), uiState.message, Toast.LENGTH_LONG)
                                .show()
                            viewModel.clearMessages()
                        }

                    }

                    if (uiState.isLoading) {
                        binding.progressBarLoadingBus.isVisible = true
                    } else {
                        binding.progressBarLoadingBus.isInvisible = true
                    }
                }
            }

        }
    }


    private fun setupMap() {
        val mapFragment = childFragmentManager.findFragmentById(
            R.id.map_fragment_container
        ) as? SupportMapFragment

        mapFragment?.getMapAsync { googleMap ->

            val clusterManager = ClusterManager<BusWithLine>(requireContext(), googleMap)

            clusterManager.renderer =
                BusRenderer(
                    requireContext(),
                    googleMap,
                    clusterManager
                )

            clusterManager.markerCollection.setInfoWindowAdapter(
                MarkerInfoWindowAdapter(
                    requireContext()
                )
            )

            observerBus(clusterManager)

            googleMap.setOnCameraIdleListener {
                clusterManager.onCameraIdle()
            }
        }
    }

}