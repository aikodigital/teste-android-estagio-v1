package com.devandroid.test_aiko.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devandroid.test_aiko.R
import com.devandroid.test_aiko.ui.adapters.PositionVehicleAdapter
import com.devandroid.test_aiko.databinding.FragmentPositionLineBinding
import com.devandroid.test_aiko.viewModels.PositionVehicleViewModel


class PositionVehicleFragment : Fragment() {
    private var _binding : FragmentPositionLineBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PositionVehicleViewModel
    private lateinit var positionVehicleAdapter: PositionVehicleAdapter
    private lateinit var recyclerView : RecyclerView
    private lateinit var btnGoToMap : Button
    private lateinit var txtTotalVehicles : TextView
    private lateinit var txtVehiclesTxt : TextView
    private lateinit var btnSearch : Button
    private lateinit var txtSearch : TextView
    private lateinit var progressBar: ProgressBar
    private var totalVehicles = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPositionLineBinding.inflate(layoutInflater,container,false)
        recyclerView = binding.rvItem
        initClicks()
        navigateToMap()
        initRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(PositionVehicleViewModel::class.java)

        getPositionsVehicles()
        btnSearch.setOnClickListener {
            btnSearch.isVisible = false
            txtSearch.isVisible = false
            progressBar.isVisible = true
            viewModel.getPosition()
            recyclerView.isVisible = true
            txtTotalVehicles.isVisible = true
            txtVehiclesTxt.isVisible = true
        }
    }

    private fun navigateToMap() {
        btnGoToMap.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_mapsFragment)
        }
    }
    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        positionVehicleAdapter = PositionVehicleAdapter(emptyList())
        recyclerView.adapter = positionVehicleAdapter
    }

    private fun initClicks(){
        btnGoToMap = binding.btnGoToMap
        recyclerView = binding.rvItem
        txtTotalVehicles = binding.textTotalVehicles
        progressBar = binding.progressBar
        txtSearch = binding.txtSearch
        btnSearch = binding.btnSearch
        txtVehiclesTxt = binding.txtVehiclesTxt
    }


    private fun getPositionsVehicles() {
        viewModel.positionResponse.observe(viewLifecycleOwner) { positionResponse ->
            val vehicles = positionResponse?.l?.flatMap { line ->
                line.vs
            }
            if (vehicles != null) {
                positionVehicleAdapter.updateVehicles(vehicles)
                progressBar.isVisible = false
                totalVehicles = vehicles.size
                txtTotalVehicles.text = totalVehicles.toString()
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}