package com.exemple.urbanbus.ui.lines

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.exemple.urbanbus.MainActivity
import com.exemple.urbanbus.R
import com.exemple.urbanbus.adapters.MarkerIconConverter
import com.exemple.urbanbus.data.models.VehicleArrival
import com.exemple.urbanbus.databinding.FragmentLineBinding
import com.exemple.urbanbus.utils.UiState
import com.exemple.urbanbus.utils.limitTitleLength
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LineFragment : Fragment(), OnMapReadyCallback {
    private val args: LineFragmentArgs by navArgs()
    private val viewModel: LinesViewModel by viewModels()
    private var _binding: FragmentLineBinding? = null
    private val binding get() = _binding!!

    private lateinit var map: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLineBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        manageMap()

        val lineSelected = args.busLineData
        val lineName = "${lineSelected.sign}-${lineSelected.signId}"

        viewModel.getLinePosition(lineSelected.code)

        // faz a atualizacao do titulo na app bar para o nome da linha
        (activity as MainActivity).updateToolbarTitle(
            lineName.limitTitleLength(
                15
            )
        )

        // determina o terminal de saida e chegada conforme o parametro "direction"
        if (lineSelected.direction == 1) {
            binding.startTerminal.text = lineSelected.mainTerminal
            binding.endTerminal.text = lineSelected.secondaryTerminal
        } else {
            binding.startTerminal.text = lineSelected.secondaryTerminal
            binding.endTerminal.text = lineSelected.mainTerminal
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        observer()
    }

    private fun manageMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun setBusPositions(vehicles: List<VehicleArrival>) {
        // bounds serve para armazena os pins ja existentes para por exemplo colocar a camera em uma posicao especifica
        val boundsBuilder = LatLngBounds.Builder()
        val icon = MarkerIconConverter(requireContext()).execute(R.drawable.bus_stop_pin_8110)

        vehicles.forEach { vehicle ->
            val position = LatLng(vehicle.latitude, vehicle.longitude)
            map.addMarker(MarkerOptions().position(position).title(vehicle.prefix).icon(icon))
            boundsBuilder.include(position)
        }

        map.moveCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 200))
    }

    private fun observer() {
        viewModel.linePositions.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.loading.root.visibility = View.VISIBLE
                }

                is UiState.Success -> {
                    if (state.data.isEmpty()) {
                        binding.mapContainer.visibility = View.GONE
                        binding.notFoundPositionsLabel.visibility = View.VISIBLE
                    } else {
                        binding.mapContainer.visibility = View.VISIBLE
                        binding.notFoundPositionsLabel.visibility = View.GONE
                        setBusPositions(state.data)
                    }
                    binding.loading.root.visibility = View.GONE
                }

                is UiState.Failure -> {
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