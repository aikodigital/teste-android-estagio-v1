package com.exemple.urbanbus.ui.stops

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.exemple.urbanbus.R
import com.exemple.urbanbus.adapters.BusStopAdapter
import com.exemple.urbanbus.databinding.FragmentSearchStopsBinding
import com.exemple.urbanbus.utils.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchStopsFragment : Fragment() {
    private val viewModel: StopsViewModel by viewModels()
    private var _binding: FragmentSearchStopsBinding? = null
    private val binding get() = _binding!!

    private val stopAdapter = BusStopAdapter {
        val bundle = Bundle()
        bundle.putParcelable("stopBusData", it)
        findNavController().navigate(R.id.action_searchStopsFragment_to_stopFragment, bundle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchStopsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()

        binding.stopsSearchInput.setEndIconOnClickListener {
            viewModel.getStops(binding.stopsSearchInput.editText?.text.toString())
        }

        binding.stopsSearchInput.editText?.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                (event != null && event.action == KeyEvent.ACTION_DOWN &&
                        event.keyCode == KeyEvent.KEYCODE_ENTER)
            ) {
                viewModel.getStops(binding.stopsSearchInput.editText?.text.toString())
                binding.stopsSearchInput.editText?.text?.clear()
                return@setOnEditorActionListener true
            }
            false
        }

        binding.stopsList.adapter = stopAdapter
    }

    override fun onResume() {
        super.onResume()
        viewModel.getStops(binding.stopsSearchInput.editText?.text.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun observer() {
        viewModel.stops.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.loading.root.visibility = View.VISIBLE
                }

                is UiState.Success -> {
                    binding.apply {
                        if (state.data.isEmpty()) {
                            warningImage.visibility = View.VISIBLE
                            warningImage.setImageResource(R.drawable.empty_box)
                            warningLabel.visibility = View.VISIBLE
                            warningLabel.text = getString(R.string.empty_label)
                        } else {
                            stopsList.visibility = View.VISIBLE
                            warningImage.visibility = View.GONE
                            warningLabel.visibility = View.GONE
                        }
                    }
                    binding.loading.root.visibility = View.GONE
                    stopAdapter.setStopList(state.data)
                }

                is UiState.Failure -> {
                    binding.apply {
                        stopsList.visibility = View.GONE
                        warningLabel.visibility = View.VISIBLE
                        warningLabel.text = getString(R.string.try_again_label)
                        warningImage.visibility = View.VISIBLE
                        warningImage.setImageResource(R.drawable.no_network_connection)
                    }

                    when (state) {
                        is UiState.Failure.NetworkError -> {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.network_error_warning), Toast.LENGTH_SHORT
                            ).show()
                        }

                        is UiState.Failure.HttpError -> {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.http_error_warning), Toast.LENGTH_SHORT
                            ).show()
                        }

                        is UiState.Failure.UnknownError -> {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.unknown_error_warning), Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    binding.loading.root.visibility = View.GONE
                }
            }

        }
    }
}