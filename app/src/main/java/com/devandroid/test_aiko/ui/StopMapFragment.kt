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
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.devandroid.test_aiko.R
import com.devandroid.test_aiko.databinding.FragmentStopMapBinding
import com.devandroid.test_aiko.viewModels.StopPointViewModel

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.w3c.dom.Text

class StopMapFragment : Fragment() {
    private var _binding : FragmentStopMapBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: StopPointViewModel
    private lateinit var txtLocation : TextView
    private lateinit var txtTitle : TextView
    private lateinit var edtTextLine : EditText
    private lateinit var btnSearch : Button
    private lateinit var btnGoToMap : ImageButton
    private lateinit var progressBar : ProgressBar
    private lateinit var imgBack : ImageButton

    private val callback = OnMapReadyCallback { googleMap ->
        viewModel = ViewModelProvider(this).get(StopPointViewModel::class.java)
        stopPointPosition(googleMap)
        configMapDetails(googleMap)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStopMapBinding.inflate(layoutInflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
         initClicks()
        btnSearch.setOnClickListener {
            if(TextUtils.isEmpty(edtTextLine.text.toString())){
                edtTextLine.setError("Empty")
                edtTextLine.requestFocus()
            }else{
                progressBar.isVisible = true
                var stopPointText = edtTextLine.text.toString()
                viewModel.getStopPoints(stopPointText!!)
            }
        }

        goToMap()
    }

    private fun goToMap() {
        btnGoToMap.setOnClickListener {
            findNavController().navigate(R.id.action_stopMapFragment_to_homeFragment)
        }
    }


    private fun stopPointPosition(googleMap : GoogleMap){
        viewModel.stopPointResponse.observe(viewLifecycleOwner){ stopPoint ->
            if(stopPoint == null){
                edtTextLine.setError("Nenhum veículo encontrado nesta linha!")
            }
            val position = LatLng(stopPoint.py,stopPoint.px)
            googleMap.clear()
            googleMap.addMarker(
                MarkerOptions().position(position).title("Endereço: ${stopPoint.np}")
            )
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(position))
            progressBar.isVisible = false
            txtLocation.setText(stopPoint.ed)
            txtLocation.isVisible = true
        }
    }


    private fun initClicks(){
        edtTextLine = binding.edtTextLine
        txtTitle = binding.txtTitle
        txtLocation = binding.txtLocation
        btnSearch = binding.btnSearch
        btnGoToMap = binding.imgbLogout
        progressBar = binding.progressBar
        imgBack = binding.imgbLogout
    }

    private fun configMapDetails(googleMap: GoogleMap) {
        val spLatLng = LatLng(-23.5489, -46.6388)
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(spLatLng))
        googleMap.uiSettings.isZoomControlsEnabled = true
        googleMap.setMinZoomPreference(10.2f)
    }


}