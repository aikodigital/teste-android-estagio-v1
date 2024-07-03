package com.example.app.ui.line

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.app.databinding.FragmentLineBinding
import com.example.app.domain.model.AllLines
import com.example.app.domain.model.Bus
import com.example.app.domain.model.Line
import com.example.app.domain.model.LineAndBus
import com.example.app.util.StateView
import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LineFragment : Fragment() {
    private var _binding: FragmentLineBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LineViewModel by viewModels()
    private lateinit var adapter: LineAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getLines()
    }

    private fun getLines() {
        viewModel.getLines().observe(viewLifecycleOwner, Observer { stateView ->
            when (stateView) {
                is StateView.Loading -> {
//                    binding.progressBar.visibility = View.VISIBLE
                }

                is StateView.Success -> {
//                    binding.progressBar.visibility = View.GONE

                    stateView.data?.let { list ->
                        val lines = extractLinesList(list)
                        initRecycler(lines)
                    }
                }

                is StateView.Error -> {
//                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "Erro ao obter dados. Tente novamente mais tarde.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    private fun initRecycler(lines: List<Line>) {
        adapter = LineAdapter(lines)
        with(binding.rvLines) {
            setHasFixedSize(true)
            this.adapter = this@LineFragment.adapter
        }
    }

    private fun extractLinesList(allLines: List<AllLines>): List<Line> {
        val lines = mutableListOf<Line>()

        allLines.forEach { allLines ->
            allLines.linesRelation.forEach { line ->
                val linesList = Line(
                    line?.fullPlacard ?: "",
                    line?.lineCode ?: 0,
                    line?.lineDirection ?: 0,
                    line?.lineDestination ?: "",
                    line?.lineOrigin ?: "",
                    line?.numberBuses ?: 0,
                    emptyList()
                )

                lines.add(linesList)
            }
        }
        return lines
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}