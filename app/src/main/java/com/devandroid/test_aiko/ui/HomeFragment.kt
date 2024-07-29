package com.devandroid.test_aiko.ui



import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.devandroid.test_aiko.R
import com.devandroid.test_aiko.ui.adapters.ViewPagerAdapter
import com.devandroid.test_aiko.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    private fun configTabLayout(){
        val adapter = ViewPagerAdapter(requireActivity())
        binding.viewPager.adapter = adapter

        adapter.addFragment(PositionVehicleFragment(), "Veja as posições dos Veículos no Mapa")
        adapter.addFragment(ArrivalForecastFragment(), title = "Veja as Paradas\n e Previsões")

        binding.viewPager.offscreenPageLimit = adapter.itemCount
        TabLayoutMediator(
            binding.tabs, binding.viewPager
        ){tab, position ->
            tab.text = adapter.getTitle(position)
        }.attach()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configTabLayout()
        auth = Firebase.auth
        initClicks()
    }

    private fun initClicks(){
        binding.imgbLogout.setOnClickListener{
            logoutUser()
        }

    }

    private fun logoutUser(){
        auth.signOut()
        findNavController().navigate(R.id.splashFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}