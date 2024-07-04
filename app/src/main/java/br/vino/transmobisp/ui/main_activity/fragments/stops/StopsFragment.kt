package br.vino.transmobisp.ui.main_activity.fragments.stops

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import br.vino.transmobisp.databinding.FragmentStopsBinding
import br.vino.transmobisp.model.Stop
import br.vino.transmobisp.ui.line_list_from_stop_activity.LineListFromStopActivity
import br.vino.transmobisp.ui.stop_vehicles_forecast_activity.StopVehiclesForecastActivity
import com.google.android.material.snackbar.Snackbar

class StopsFragment : Fragment(), StopsAdapter.OnItemClickListener {

    private var _binding: FragmentStopsBinding? = null
    private val viewModel by viewModels<StopsViewModel>()
    private lateinit var adapter : StopsAdapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentStopsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.stopsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = StopsAdapter(emptyList(), this)
        binding.stopsRecyclerView.adapter = adapter

        binding.stopsSearchView.isIconified = false

        setupListeners()

        return root
    }

    private fun setupListeners(){
        viewModel.stops.observe(viewLifecycleOwner){stopList ->
            adapter.updateStopsFromline(stopList.toList())
        }

        binding.stopsSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String?): Boolean {
                context?.hideKeyboard(binding.stopsSearchView)
                if(text.toString().isNotEmpty()){
                    Snackbar.make(binding.stopsSearchView, "Buscando paradas", Snackbar.LENGTH_LONG).show()
                    viewModel.getStopsWithTerm(text.toString())
                }else{
                    Snackbar.make(binding.stopsSearchView, "Digite um nome válido", Snackbar.LENGTH_LONG).show()
                }

                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(stop: Stop) {
        val intent = Intent(requireContext(), LineListFromStopActivity::class.java)
        intent.putExtra("stop", stop)
        startActivity(intent)
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}