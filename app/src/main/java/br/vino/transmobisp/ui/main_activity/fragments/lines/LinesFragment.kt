package br.vino.transmobisp.ui.main_activity.fragments.lines

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import br.vino.transmobisp.databinding.FragmentLinesBinding
import br.vino.transmobisp.model.VehicleLine
import br.vino.transmobisp.ui.stops_from_line_activity.StopsFromLineActivity
import com.google.android.material.snackbar.Snackbar

class LinesFragment : Fragment(), LineListAdapter.OnItemClickListener {

    private var _binding: FragmentLinesBinding? = null
    private val viewModel by viewModels<LinesViewModel>()
    private lateinit var adapter: LineListAdapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLinesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = LineListAdapter(emptyList(), this)
        binding.recyclerView.adapter = adapter

        viewModel.fetchVeiculosLine()
        setupListeners()

        return root
    }

    private fun setupListeners(){
        viewModel.vehiclesLine.observe(viewLifecycleOwner){vehiclesLineList ->
            if (vehiclesLineList.isNotEmpty()){
                adapter.updateVehiclesLine(vehiclesLineList)
            }else{
                Snackbar.make(binding.recyclerView, "Nenhuma informação encontrada", Snackbar.LENGTH_LONG).show()
            }
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(text: String?): Boolean {
                adapter.filter(text.orEmpty())
                return true
            }

        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(vehicleLine: VehicleLine) {
        val intent = Intent(requireContext(), StopsFromLineActivity::class.java)
        intent.putExtra("vehicleLine", vehicleLine)
        startActivity(intent)
    }


}