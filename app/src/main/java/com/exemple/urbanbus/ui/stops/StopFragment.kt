package com.exemple.urbanbus.ui.stops

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
import com.exemple.urbanbus.adapters.BusStopArrivalAdapter
import com.exemple.urbanbus.adapters.MarkerIconConverter
import com.exemple.urbanbus.databinding.FragmentStopBinding
import com.exemple.urbanbus.utils.UiState
import com.exemple.urbanbus.utils.getBusStopTitle
import com.exemple.urbanbus.utils.limitTitleLength
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StopFragment : Fragment(), OnMapReadyCallback {
    private val args: StopFragmentArgs by navArgs()
    private val viewModel: StopsViewModel by viewModels()
    private var _binding: FragmentStopBinding? = null
    private val binding get() = _binding!!

    private lateinit var map: GoogleMap
    private val arrivalAdapter = BusStopArrivalAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStopBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        manageMap()

        val stopData = args.stopBusData

        (activity as MainActivity).updateToolbarTitle(
            getBusStopTitle(stopData).limitTitleLength(
                15
            )
        )

        viewModel.getLineArrival(stopData.code)

        binding.stopLocation.text = stopData.address
        binding.arrivalList.adapter = arrivalAdapter

        binding.tryAgainBtn.setOnClickListener {
            viewModel.getLineArrival(stopData.code)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        val position = LatLng(args.stopBusData.latitude, args.stopBusData.longitude)
        val icon = MarkerIconConverter(requireContext()).execute(R.drawable.ic_location_on_24dp)

        map.addMarker(
            MarkerOptions().position(position).title(getBusStopTitle(args.stopBusData)).icon(icon)
        )
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 16f))
    }

    private fun manageMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun observer() {
        viewModel.lineArrival.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.loading.root.visibility = View.VISIBLE
                }

                is UiState.Success -> {
                    binding.apply {
                        imageError.visibility = View.GONE
                        tryAgainBtn.visibility = View.GONE
                        arrivalList.visibility = View.VISIBLE
                        arrivalTitle.visibility = View.VISIBLE
                    }
                    arrivalAdapter.setBusStopArrivalList(state.data)
                    binding.loading.root.visibility = View.GONE
                }

                is UiState.Failure -> {
                    binding.apply {
                        imageError.visibility = View.VISIBLE
                        tryAgainBtn.visibility = View.VISIBLE
                        arrivalList.visibility = View.GONE
                        arrivalTitle.visibility = View.GONE
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