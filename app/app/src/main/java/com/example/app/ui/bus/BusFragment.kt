package com.example.app.ui.bus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.app.databinding.FragmentBusBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BusFragment : Fragment() {
    private var _binding: FragmentBusBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BusViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBusBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.authState.observe(viewLifecycleOwner, Observer { isAuthenticated ->
            if (isAuthenticated) {
                Toast.makeText(requireContext(), "True", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Usuário não autenticado.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}