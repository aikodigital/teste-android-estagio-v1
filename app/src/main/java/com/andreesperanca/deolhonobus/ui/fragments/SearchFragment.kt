package com.andreesperanca.deolhonobus.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.andreesperanca.deolhonobus.R
import com.andreesperanca.deolhonobus.adapters.BusLineAdapter
import com.andreesperanca.deolhonobus.adapters.BusStopAdapter
import com.andreesperanca.deolhonobus.databinding.FragmentSearchBinding
import com.andreesperanca.deolhonobus.ui.viewmodels.SearchViewModel
import com.andreesperanca.deolhonobus.util.Resource
import com.andreesperanca.deolhonobus.util.hideKeyboard
import com.andreesperanca.deolhonobus.util.snackBarCreator
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private val binding by lazy { FragmentSearchBinding.inflate(layoutInflater) }
    private val adapter by lazy { BusLineAdapter() }
    private val adapterBusStop by lazy { BusStopAdapter() }
    private val viewModel: SearchViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //OBSERVABLES
        viewModel.fetchBusStopWithBusLineCode.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    adapterBusStop.updateList(it.data)
                    configureBusStopAdapter(adapterBusStop)
                    binding.progressBar.visibility = View.INVISIBLE
                }
                is Resource.Error -> {
                    snackBarCreator(binding.root, it.message.toString())
                    binding.progressBar.visibility = View.INVISIBLE
                }
            }
        }

        viewModel.fetchBusStopWithHallCode.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    adapterBusStop.updateList(it.data)
                    configureBusStopAdapter(adapterBusStop)
                    binding.progressBar.visibility = View.INVISIBLE
                }
                is Resource.Error -> {
                    snackBarCreator(binding.root, it.message.toString())
                    binding.progressBar.visibility = View.INVISIBLE
                }
            }
        }

        viewModel.fetchBusStopWithNameOrAddress.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    adapterBusStop.updateList(it.data)
                    configureBusStopAdapter(adapterBusStop)
                    binding.progressBar.visibility = View.INVISIBLE
                }
                is Resource.Error -> {
                    snackBarCreator(binding.root, it.message.toString())
                    binding.progressBar.visibility = View.INVISIBLE
                }
            }
        }

        viewModel.fetchBusLineWithDenominationOrName.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    adapter.updateList(it.data)
                    configureBusLineAdapter(adapter)
                    binding.progressBar.visibility = View.INVISIBLE
                }
                is Resource.Error -> {
                    snackBarCreator(binding.root, it.message.toString())
                    binding.progressBar.visibility = View.INVISIBLE
                }
            }
        }

        viewModel.authResult.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.centerProgressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.btnSearch.isEnabled = true
                    binding.centerProgressBar.visibility = View.INVISIBLE
                }
                is Resource.Error -> {
                    snackBarCreator(binding.root, it.message.toString())
                    binding.centerProgressBar.visibility = View.INVISIBLE
                }
            }

        }
    }

    override fun onStart() {
        super.onStart()
        getAuthInApi()
        configureListeners()
        configureAdapter()
    }

    private fun getAuthInApi() {
        viewModel.getAuthInApi()
    }

    private fun configureListeners() {
        binding.rgSearchType.setOnCheckedChangeListener { _, checkedId ->
            if (binding.busStop.id == checkedId) {
                binding.rgSearchBusStopSelected.visibility = View.VISIBLE
                binding.rgSearchLineSelected.visibility = View.INVISIBLE
            } else {
                binding.rgSearchBusStopSelected.visibility = View.INVISIBLE
                binding.rgSearchLineSelected.visibility = View.VISIBLE
            }
        }
        binding.btnSearch.setOnClickListener {
            if (binding.lines.isChecked) {
                viewModel.getBusLineWithDenominationOrNumber(binding.etSearchBar.text.toString())
            } else {
                when (true) {
                    binding.rbLineName.isChecked -> {
                        viewModel.getBusStopWithBusLineCode(binding.etSearchBar.text.toString())
                    }
                    binding.rbBusStop.isChecked -> {
                        viewModel.getBusStopWithAddressOrName(binding.etSearchBar.text.toString())
                    }
                    else -> { viewModel.getBusStopWithHallCode(binding.etSearchBar.text.toString()) }
                }
            }
            hideKeyboard(binding.root)
        }
    }

    private fun configureAdapter() {
        val divisor = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        binding.rvSearchFragment.layoutManager = LinearLayoutManager(requireContext(), VERTICAL, false)
        binding.rvSearchFragment.addItemDecoration(divisor)
    }

    private fun configureBusLineAdapter(adapter: BusLineAdapter) {
        binding.rvSearchFragment.adapter = adapter
        if (adapter.itemCount == 0) {
            binding.adapterIsEmpty.tvSearchControl.text = getString(R.string.nothingFound)
            binding.adapterIsEmpty.ivSearchControl.
            setImageDrawable(AppCompatResources.
            getDrawable(requireContext(), R.drawable.ic_nothing_found))
            binding.adapterIsEmpty.root.visibility = View.VISIBLE
        } else {
            binding.adapterIsEmpty.root.visibility = View.INVISIBLE
        }
    }

    private fun configureBusStopAdapter(adapter: BusStopAdapter) {
        binding.rvSearchFragment.adapter = adapter
        if (adapter.itemCount == 0) {
            binding.adapterIsEmpty.tvSearchControl.text = getString(R.string.nothingFound)
            binding.adapterIsEmpty.ivSearchControl.
            setImageDrawable(AppCompatResources.
            getDrawable(requireContext(), R.drawable.ic_nothing_found))
            binding.adapterIsEmpty.root.visibility = View.VISIBLE
        } else {
            binding.adapterIsEmpty.root.visibility = View.INVISIBLE
        }
    }

}
