package com.conti.onibusspemtemporeal.ui.fragments.map

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.conti.onibusspemtemporeal.R
import com.conti.onibusspemtemporeal.databinding.FragmentMapBinding
import com.conti.onibusspemtemporeal.ui.viewModel.OnibusSpViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint


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



    private fun setupMap() {
        val mapFragment = childFragmentManager.findFragmentById(
            R.id.map_fragment_container
        ) as? SupportMapFragment

        mapFragment?.getMapAsync { googleMap ->
            addMarkers(googleMap)
        }
    }


    private fun addMarkers(googleMap: GoogleMap) {
        val latLng = LatLng(-23.114334, -47.255380)

        googleMap.addMarker(
            MarkerOptions()
                .title("minha casa")
                .position(latLng)

        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

    }

}