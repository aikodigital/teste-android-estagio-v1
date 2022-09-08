package com.andreesperanca.deolhonobus.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andreesperanca.deolhonobus.MapsActivity
import com.andreesperanca.deolhonobus.R
import com.andreesperanca.deolhonobus.adapters.BusStopAdapter
import com.andreesperanca.deolhonobus.databinding.FragmentBusDetailsBinding
import com.andreesperanca.deolhonobus.models.MarkerInGmaps
import com.andreesperanca.deolhonobus.ui.viewmodels.BusDetailsViewModel
import com.andreesperanca.deolhonobus.util.Resource
import com.andreesperanca.deolhonobus.util.snackBarCreator
import org.koin.androidx.viewmodel.ext.android.viewModel

class BusDetailsFragment : Fragment() {

    private val args: BusDetailsFragmentArgs by navArgs()
    private val adapter by lazy { BusStopAdapter() }
    private val viewModel: BusDetailsViewModel by viewModel()
    private val binding by lazy { FragmentBusDetailsBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //OBSERVABLES
        viewModel.fetchBusLinePosition.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    it.data?.let { result ->
                        if (result.isEmpty()) {
                            snackBarCreator(binding.root, getString(R.string.noHaveData))
                        } else {
                            val intent = Intent(requireContext(), MapsActivity::class.java)
                            intent.putExtra(
                                "markersForTheMap", MarkerInGmaps(
                                    title = args.bus.firstLabel,
                                    listMarker = result
                                )
                            )
                            startActivity(intent)
                        }
                        binding.progressBar.visibility = View.INVISIBLE
                    }
                }
                is Resource.Error -> {
                    snackBarCreator(binding.root, it.message.toString())
                    binding.progressBar.visibility = View.INVISIBLE
                }
                else -> {snackBarCreator(binding.root, it?.message.toString())}
            }
        }

        viewModel.searchBusStop.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    adapter.updateList(it.data)
                    binding.progressBar.visibility = View.INVISIBLE
                }
                is Resource.Error -> {
                    snackBarCreator(binding.root, it.message.toString())
                    binding.progressBar.visibility = View.INVISIBLE
                }
            }
        }

        viewModel.isFavorite.observe(viewLifecycleOwner) { isFavorite ->
            if (isFavorite == true) {
                binding.btnFavorite.setImageDrawable(resources.getDrawable(R.drawable.ic_is_favorite))
            } else {
                binding.btnFavorite.setImageDrawable(resources.getDrawable(R.drawable.ic_not_favorite))
            }
        }
    }

    override fun onStart() {
        super.onStart()
        fetchIfBusLineIsFavorite()
        fetchBusLineStopWithBusLineCode()
        configureListeners()
        configureRecyclerView()
        linksInformation()
    }

    private fun fetchIfBusLineIsFavorite() {
        viewModel.favoriteVerify(args.bus)
    }

    private fun fetchBusLineStopWithBusLineCode() {
        viewModel.getBusStopWithBusLineCode(args.bus.idCode.toString())
    }

    private fun configureListeners() {
        binding.btnLocalize.setOnClickListener {
            viewModel.getBusLinePositionWithBusLineCode(args.bus.idCode.toString())
        }

        binding.btnFavorite.setOnClickListener {
            viewModel.favoriteBusLine(args.bus)
        }
    }

    private fun configureRecyclerView() {
        val divisor = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        binding.rvBusStop.adapter = adapter
        binding.rvBusStop.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.rvBusStop.addItemDecoration(divisor)
    }

    private fun linksInformation() {
        binding.tvIdBus.text = getString(R.string.idBus, args.bus.idCode.toString())
        binding.tvNumberLine.text = getString(R.string.busNumber, args.bus.firstLabel)

        if (args.bus.CircularRoute) {
            binding.tvOperationType.text = getString(R.string.operationType, "Sim")
        } else {
            binding.tvOperationType.text = getString(R.string.operationType, "Não")
        }
        if (args.bus.secondLabel == "10") {
            binding.tvOperationMode.text = getString(R.string.operationMode, "Base")
        } else {
            binding.tvOperationMode.text = getString(R.string.operationMode, "Atendimento")
        }
        if (args.bus.direction == 1) {
            binding.tvOrigin.text =
                binding.root.context.getString(R.string.origin, args.bus.mainTerminal)
            binding.tvDestination.text =
                binding.root.context.getString(R.string.destination, args.bus.secondaryTerminal)
        } else {
            binding.tvOrigin.text =
                binding.root.context.getString(R.string.origin, args.bus.secondaryTerminal)
            binding.tvDestination.text =
                binding.root.context.getString(R.string.destination, args.bus.mainTerminal)
        }
    }
}