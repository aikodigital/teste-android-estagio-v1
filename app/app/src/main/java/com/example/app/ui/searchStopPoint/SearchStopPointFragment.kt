package com.example.app.ui.searchStopPoint

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.app.data.model.ApiResponse
import com.example.app.data.model.BusWithTimeResponse
import com.example.app.databinding.FragmentSearchStopPointBinding
import com.example.app.util.StateView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchStopPointFragment : Fragment() {
    private var _binding: FragmentSearchStopPointBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchStopPointViewModel by viewModels()
    private lateinit var adapter: SearchStopPointAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchStopPointBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    getStopPointByLine(query)
                    return true
                } else {
                    return false
                }
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    private fun getStopPointByLine(query: String) {
        viewModel.getBusByStopPoint(query).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.rvLines.visibility = View.GONE
                    binding.tvInfo.visibility = View.GONE
                }

                is StateView.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rvLines.visibility = View.VISIBLE
                    binding.tvInfo.visibility = View.GONE

                    stateView.data?.let { list ->
                        val busListWithTime = extractStopPointList(list)
                        initRecycler(busListWithTime)
                    }
                }

                is StateView.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rvLines.visibility = View.VISIBLE
                    binding.tvInfo.visibility = View.VISIBLE

                    Toast.makeText(
                        requireContext(),
                        "Erro ao obter dados. Tente novamente mais tarde.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun initRecycler(busList: List<BusWithTimeResponse>) {
        adapter = SearchStopPointAdapter(busList)

        with(binding.rvLines) {
            setHasFixedSize(true)
            this.adapter = this@SearchStopPointFragment.adapter
        }
    }

    private fun extractStopPointList(stopPoints: List<ApiResponse>): List<BusWithTimeResponse> {
        val busList = mutableListOf<BusWithTimeResponse>()

        stopPoints.forEach { apiResponse ->
            apiResponse.stopPoint?.lines?.forEach { lineData ->
                lineData.busList?.forEach { vehicle ->
                    val busWithTime = BusWithTimeResponse(
                        vehicle.busPrefix,
                        vehicle.arrivalForecast,
                        vehicle.isAccessible,
                        vehicle.utcRequestHour,
                        vehicle.lat,
                        vehicle.lng
                    )
                    busList.add(busWithTime)
                }
            }
        }

        return busList
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}