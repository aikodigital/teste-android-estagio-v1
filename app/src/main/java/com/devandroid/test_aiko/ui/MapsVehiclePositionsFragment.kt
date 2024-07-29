package com.devandroid.test_aiko.ui

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.devandroid.test_aiko.R
import com.devandroid.test_aiko.databinding.FragmentVehiclePositionMapsBinding
import com.devandroid.test_aiko.models.PositionLine
import com.devandroid.test_aiko.models.Vehicle
import com.devandroid.test_aiko.models.VehicleForecast
import com.devandroid.test_aiko.viewModels.MapsVehiclePositionViewModel

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsVehiclePositionsFragment : Fragment() {
    private var _binding : FragmentVehiclePositionMapsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : MapsVehiclePositionViewModel
    private var totalVehicles = 0

    private lateinit var txtTotalVehicles : TextView
    private lateinit var txtTimeLastVehicle : TextView
    private lateinit var txtTotalVehiclesTxt : TextView
    private lateinit var txtTitle : TextView
    private lateinit var txtLastRegister : TextView
    private lateinit var edtNumberLine : EditText
    private lateinit var btnSearch : Button
    private lateinit var progressBar : ProgressBar
    private lateinit var imgBack : ImageButton

    private var numLine : Int? = null

    private val callback = OnMapReadyCallback { googleMap ->
        viewModel = ViewModelProvider(this).get(MapsVehiclePositionViewModel::class.java)

        positionWithLine(googleMap)
        configMapDetails(googleMap)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        initClicks()

        btnSearch.setOnClickListener {
            if(TextUtils.isEmpty(edtNumberLine.text.toString())){
                edtNumberLine.setError("Empty")
                edtNumberLine.requestFocus()
            }else{
                progressBar.isVisible
                numLine = edtNumberLine.text.toString().toInt()
                viewModel.getPositionLine(numLine!!)
            }
        }


        imgBack.setOnClickListener{
            findNavController().navigate(R.id.action_mapsFragment_to_homeFragment)
        }
        txtTotalVehicles.text = totalVehicles.toString()
    }

    private fun configMapDetails(googleMap: GoogleMap) {
        val spLatLng = LatLng(-23.5489, -46.6388)
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(spLatLng))
        googleMap.uiSettings.isZoomControlsEnabled = true
        googleMap.setMinZoomPreference(10.2f)
    }



    private fun positionWithLine(googleMap : GoogleMap){
            viewModel.positionLineResponse.observe(viewLifecycleOwner){ positionLineResponse ->
                if(positionLineResponse.vs.size == 0){
                    edtNumberLine.setError("Nenhum veículo encontrado nesta linha!")
                }
                positionLineResponse.vs.forEach { vehicle ->
                    changeVisibilitysResponseOk(positionLineResponse,vehicle)
                    setMarksOnMap(vehicle, googleMap)
                }
            }
    }

    private fun setMarksOnMap(
        vehicle: VehicleForecast,
        googleMap: GoogleMap
    ) {
        val position = LatLng(vehicle.py, vehicle.px)
        googleMap.addMarker(
            MarkerOptions().position(position).title("Veículo: ${vehicle.p}")
        )
        val spLatLng = LatLng(vehicle.py, vehicle.px)
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(spLatLng))
    }

    private fun changeVisibilitysResponseOk(positionLineResponse: PositionLine, vehicle: VehicleForecast) {
        progressBar.isVisible = false
        edtNumberLine.isVisible = false
        txtTitle.isVisible = false
        btnSearch.isVisible = false
        totalVehicles = positionLineResponse.vs.size
        txtTimeLastVehicle.isVisible = true
        txtTimeLastVehicle.text = vehicle.ta.substring(11,19)
        txtTotalVehicles.isVisible = true
        txtTotalVehicles.text = totalVehicles.toString()
        txtLastRegister.isVisible = true
        txtTotalVehiclesTxt.isVisible = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVehiclePositionMapsBinding.inflate(layoutInflater,container,false)
        return binding.root

    }

    private fun initClicks(){
        txtTotalVehicles = binding.textTotalVehicles
        txtTimeLastVehicle = binding.textTimeLastVehicle
        txtTotalVehiclesTxt = binding.txtTotalVehiclesTxt
        txtLastRegister = binding.txtLastRegister
        txtTitle = binding.txtTitle
        edtNumberLine = binding.edtNumberLine
        btnSearch = binding.btnSearch
        progressBar = binding.progressBar
        imgBack = binding.imgbLogout
    }


}