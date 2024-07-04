package com.exemple.urbanbus.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.exemple.urbanbus.R
import com.exemple.urbanbus.adapters.BusStopAdapter
import com.exemple.urbanbus.adapters.MarkerIconConverter
import com.exemple.urbanbus.data.models.BusStop
import com.exemple.urbanbus.databinding.FragmentHomeBinding
import com.exemple.urbanbus.utils.UiState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), OnMapReadyCallback {
    private val viewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var map: GoogleMap

    // adapter para a criacao da lista de paradas
    private val stopAdapter = BusStopAdapter {
        val bundle = Bundle()
        bundle.putParcelable("stopBusData", it)
        findNavController().navigate(R.id.action_homeFragment_to_stopFragment, bundle)
    }

    companion object {
        private val SAO_PAULO_LOCATION = LatLng(-23.589811, -46.638537)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        customizationBottomSheet()
        manageMap()
        // realiza a chamada para buscar todas as paradas
        viewModel.getAllBusStops()

        binding.stopsBtn.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchStopsFragment)
        }

        binding.linesBtn.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchLinesFragment)
        }

        binding.stopsList.adapter = stopAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        // move a camera para o centro de sao paulo
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(SAO_PAULO_LOCATION, 12f))
    }

    private fun manageMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    // aplica regras a bottom sheet
    private fun customizationBottomSheet() {
        val standardBottomSheet = binding.bottomSheet
        val standardBottomSheetBehavior = BottomSheetBehavior.from(standardBottomSheet)

        standardBottomSheetBehavior.isHideable = false
        standardBottomSheetBehavior.peekHeight =
            resources.getDimensionPixelSize(R.dimen.bottom_sheet_peek_height)
        standardBottomSheetBehavior.maxHeight =
            resources.getDimensionPixelSize(R.dimen.bottom_sheet_max_height)
    }

    // realiza a adicao dos pinos no mapa
    private fun addMarkers(busStops: List<BusStop>) {
        for (busStop in busStops) {
            val position = LatLng(busStop.latitude, busStop.longitude)
            val titleToShow = busStop.name.ifBlank {
                busStop.code.toString()
            }
            val icon = MarkerIconConverter(requireContext()).execute(R.drawable.ic_location_on_24dp)
            map.addMarker(MarkerOptions().position(position).title(titleToShow).icon(icon))

        }
    }

    private fun observer() {
        viewModel.stops.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.loading.root.visibility = View.VISIBLE
                    binding.linesBtn.isEnabled = false
                    binding.stopsBtn.isEnabled = false
                }

                is UiState.Success -> {
                    // ativa os botoes de interacao e mostra a lista de paradas
                    binding.apply {
                        stopsList.visibility = View.VISIBLE
                        stopsBtn.visibility = View.VISIBLE
                        linesBtn.visibility = View.VISIBLE
                        errorImage.visibility = View.GONE
                        tryAgainBtn.visibility = View.GONE
                        linesBtn.isEnabled = true
                        stopsBtn.isEnabled = true

                    }
                    addMarkers(state.data)
                    stopAdapter.setStopList(state.data)
                    binding.loading.root.visibility = View.GONE
                }

                is UiState.Failure -> {
                    // desativa os botoes de interacao e esconde a lista de paradas
                    binding.apply {
                        stopsList.visibility = View.GONE
                        stopsBtn.visibility = View.GONE
                        linesBtn.visibility = View.GONE
                        errorImage.visibility = View.VISIBLE
                        tryAgainBtn.visibility = View.VISIBLE
                        tryAgainBtn.setOnClickListener {
                            viewModel.getAllBusStops()
                        }
                    }

                    when (state) {
                        is UiState.Failure.NetworkError -> {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.network_error_warning), Toast.LENGTH_SHORT
                            ).show()
                        }

                        is UiState.Failure.HttpError -> {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.http_error_warning), Toast.LENGTH_SHORT
                            ).show()
                        }

                        is UiState.Failure.UnknownError -> {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.unknown_error_warning), Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    binding.loading.root.visibility = View.GONE
                }
            }
        }
    }
}