package com.conti.onibusspemtemporeal.ui.fragments.route


import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.conti.onibusspemtemporeal.R
import com.conti.onibusspemtemporeal.databinding.FragmentRouteBusBinding
import com.conti.onibusspemtemporeal.ui.adapter.BusRouteAdapter
import com.conti.onibusspemtemporeal.ui.viewModel.OnibusSpViewModel
import com.conti.onibusspemtemporeal.util.retrofitHandling.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RouteBusSearchDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentRouteBusBinding
    private lateinit var recyclerViewBusRoute: RecyclerView
    private lateinit var busRouteAdapter: BusRouteAdapter
    private val viewModel: OnibusSpViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialog)

    }

    override fun onStart() {
        super.onStart()
        val d: Dialog? = dialog
        if (d != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            d.window?.setLayout(width, height)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRouteBusBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backHome()
        setupRecyclerView()
        searchBusRoute()
        selectTheBusRoute()
        favoriteBusRoute()
        observerBusRoute()
        observerUiState()
    }

    private fun favoriteBusRoute() {
        busRouteAdapter.setonImageFavoriteClickListener { busRoute ->
            viewModel.favoriteBusRoute(busRoute)
        }
    }

    private fun selectTheBusRoute() {
        busRouteAdapter.setonCardClickListener { busRoute ->
            viewModel.selectTheBusRoute(busRoute.lineCod)
            dismissAllowingStateLoss()
        }
    }

    private fun backHome() {
        binding.imageButtonBackHome.setOnClickListener {
            dismiss()
        }
    }

    private fun observerBusRoute() {
        viewModel.busRoute.observe(viewLifecycleOwner) { resourceBusRoute ->
            when (resourceBusRoute) {
                is Resource.Loading -> {
                    binding.progressBarLoadingSearch.isVisible = true
                }
                is Resource.Success -> {
                    busRouteAdapter.submitList(resourceBusRoute.data!!.toMutableList())
                    binding.progressBarLoadingSearch.isInvisible = true
                }
                is Resource.Error -> {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.erro) + resourceBusRoute.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
                else -> {
                    binding.progressBarLoadingSearch.isInvisible = true
                }
            }
        }
    }

    private fun setupRecyclerView() {
        recyclerViewBusRoute = binding.recyclerViewRoute
        busRouteAdapter = BusRouteAdapter()
        recyclerViewBusRoute.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = busRouteAdapter
        }
    }

    private fun searchBusRoute() {
        binding.editTextSearchRoute.addTextChangedListener {
            viewModel.searchBusRoute(it.toString())
        }
        binding.buttonSearch.setOnClickListener {
            if (binding.editTextSearchRoute.text.isNotEmpty()) {
                viewModel.searchBusRoute(binding.editTextSearchRoute.text.toString().uppercase())
            } else {
                Toast.makeText(requireContext(), getString(R.string.field_empty), Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun observerUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.uiState.collect {
                    if (it.message.isNotEmpty()) {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                        viewModel.clearMessages()
                    }
                }
            }
        }
    }
}

