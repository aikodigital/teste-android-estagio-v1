package com.example.app.ui.line

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.app.BuildConfig
import com.example.app.databinding.FragmentLineBinding
import com.example.app.ui.bus.BusViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LineFragment : Fragment() {
    private var _binding: FragmentLineBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}