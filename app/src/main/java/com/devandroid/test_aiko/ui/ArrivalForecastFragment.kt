package com.devandroid.test_aiko.ui

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devandroid.test_aiko.R
import com.devandroid.test_aiko.ui.adapters.ArrivalForecastAdapter
import com.devandroid.test_aiko.databinding.FragmentArrivalForecastBinding
import com.devandroid.test_aiko.models.VehicleForecast
import com.devandroid.test_aiko.viewModels.ArrivalForecastViewModel


class ArrivalForecastFragment : Fragment() {
    private lateinit var viewModel:ArrivalForecastViewModel
    private lateinit var arrivalForecastAdapter: ArrivalForecastAdapter
    private var _binding : FragmentArrivalForecastBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView : RecyclerView
    private var stopCode = 0
    private var totalVehicles = 0

    private lateinit var txtSearch : TextView
    private lateinit var txtTotalVehicles : TextView
    private lateinit var txtVehiclesTxt : TextView
    private lateinit var edtStopCode : EditText
    private lateinit var btnSearch : Button
    private lateinit var btnClear : Button
    private lateinit var btnGoToMap : Button
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArrivalForecastBinding.inflate(layoutInflater, container, false)
        initRecyclerView()
        initClicks()
        navigateToMap()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ArrivalForecastViewModel::class.java]


        btnSearch.setOnClickListener {
            if(TextUtils.isEmpty(edtStopCode.text.toString())){
                edtStopCode.setError("Empty")
                edtStopCode.requestFocus()
            } else {
                progressBar.isVisible = true
                stopCode = edtStopCode.text.toString().toInt()
                viewModel.getArrivalForecast(stopCode)
                getArrivalForecastStop()
            }

            btnClear.setOnClickListener {
                arrivalForecastAdapter.clear()
                totalVehicles = 0
                stopCode = 0
                txtSearch.isVisible = true
                txtVehiclesTxt.isVisible = false
                txtTotalVehicles.isVisible = false
                edtStopCode.setText("")
            }

    }

    }

    private fun navigateToMap() {
        btnGoToMap.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_stopMapFragment)
        }
    }

    private fun getArrivalForecastStop() {
        viewModel.arrivalForecastResponse.observe(viewLifecycleOwner) { arrivalResponse ->
            arrivalResponse?.let {
                it.p?.l?.forEach { lineLocate ->
                    lineLocate.vs.forEach {
                        var vehicleForecastList : MutableList<VehicleForecast> = mutableListOf()
                        vehicleForecastList.addAll(lineLocate.vs)

                        arrivalForecastAdapter.updateVehicles(vehicleForecastList)
                        recyclerView.adapter = arrivalForecastAdapter

                        totalVehicles = vehicleForecastList.size
                        if(vehicleForecastList.isEmpty()){
                            edtStopCode.setError("Nenhuma previsão para essa parada")
                            progressBar.isVisible = false
                        } else {
                            recyclerView.isVisible = true
                            progressBar.isVisible = false
                            txtSearch.isVisible = false
                            btnClear.isVisible = true
                        }
                    }
                }
            }
            txtTotalVehicles.isVisible = true
            txtVehiclesTxt.isVisible = true
            txtTotalVehicles.text = totalVehicles.toString()
        }
    }

    private fun initRecyclerView() {
        recyclerView = binding.rvItemArrival
        recyclerView.layoutManager = LinearLayoutManager(context)
        arrivalForecastAdapter = ArrivalForecastAdapter(emptyList())
        recyclerView.adapter = arrivalForecastAdapter
    }

    private fun initClicks(){
        txtSearch = binding.txtSearch
        edtStopCode = binding.edtStopCode
        btnSearch = binding.btnSearch
        progressBar = binding.progressBar
        txtTotalVehicles = binding.textTotalVehicles
        txtVehiclesTxt = binding.txtVehiclesTxt
        btnClear = binding.btnClear
        btnGoToMap = binding.btnGoToMap
    }
}