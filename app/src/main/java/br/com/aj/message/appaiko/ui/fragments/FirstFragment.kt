package br.com.aj.message.appaiko.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import br.com.aj.message.appaiko.databinding.FragmentFirstBinding
import br.com.aj.message.appaiko.ui.fragments.TabsAdapter


class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.homeFragment.viewPager2.isNestedScrollingEnabled = false
        binding.homeFragment.viewPager2.adapter = TabsAdapter(parentFragmentManager,lifecycle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}