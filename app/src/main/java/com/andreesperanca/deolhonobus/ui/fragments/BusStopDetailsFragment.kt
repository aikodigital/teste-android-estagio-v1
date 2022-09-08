package com.andreesperanca.deolhonobus.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.andreesperanca.deolhonobus.MapsActivity
import com.andreesperanca.deolhonobus.R
import com.andreesperanca.deolhonobus.adapters.BusStopForecastAdapter
import com.andreesperanca.deolhonobus.databinding.FragmentBusStopDetailsBinding
import com.andreesperanca.deolhonobus.models.MarkerInGmaps
import com.andreesperanca.deolhonobus.models.Place
import com.andreesperanca.deolhonobus.ui.viewmodels.BusStopDetailsViewModel
import com.andreesperanca.deolhonobus.util.Resource
import com.andreesperanca.deolhonobus.util.dateStringFormatter
import com.andreesperanca.deolhonobus.util.toList
import com.google.android.gms.maps.model.LatLng
import org.koin.android.ext.android.inject

class BusStopDetailsFragment : Fragment() {

    private val binding by lazy { FragmentBusStopDetailsBinding.inflate(layoutInflater) }
    private lateinit var adapter: BusStopForecastAdapter
    private val viewModel: BusStopDetailsViewModel by inject()
    private val args: BusStopDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //OBSERVABLES
        viewModel.getForecast.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    adapter.updateList(it.data)
                    binding.progressBar.visibility = View.INVISIBLE
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(),it.message,Toast.LENGTH_LONG).show()
                    binding.progressBar.visibility = View.INVISIBLE
                }
            }
        }

        viewModel.isFavorite.observe(viewLifecycleOwner) { isFavorite ->
            if (isFavorite == true) {
                binding.btnFavoriteBusStop.setImageDrawable(resources.getDrawable(R.drawable.ic_is_favorite))
            } else {
                binding.btnFavoriteBusStop.setImageDrawable(resources.getDrawable(R.drawable.ic_not_favorite))
            }
        }
    }

    override fun onStart() {
        super.onStart()
        inputInfoBusStop()
    }
    override fun onResume() {
        super.onResume()
        fetchIfIsFavoriteBusStop()
        fetchBusLinesWithBusStopCode()
        configureRecyclerView()
        configureListeners()
    }

    private fun fetchIfIsFavoriteBusStop() {
        viewModel.favoriteVerify(args.busStop)
    }

    private fun configureListeners() {
        binding.btnLocalizeBusStop.setOnClickListener { configureLocalizeButton() }
        binding.btnFavoriteBusStop.setOnClickListener { viewModel.favoriteBusLine(args.busStop) }
    }
    private fun fetchBusLinesWithBusStopCode() {
        viewModel.getForecastWithBusStopCode(args.busStop.idCodeBusStop.toString())
    }
    private fun inputInfoBusStop() {
        binding.tvBusStopName.text = getString(R.string.busStopName, args.busStop.name)
        binding.tvAddress.text = getString(R.string.address, args.busStop.address)
        binding.tvIdCode.text = getString(R.string.idCode, args.busStop.id.toString())
    }
    private fun configureLocalizeButton() {
        val place = Place(title = args.busStop.name, LatLng(args.busStop.latitude,args.busStop.longitude))
        val intent = Intent(requireContext(), MapsActivity::class.java)
        intent.putExtra("markersForTheMap",
            MarkerInGmaps(title = args.busStop.name,
                listMarker = place.toList()
        ))
        startActivity(intent)
    }
    private fun configureRecyclerView() {
        binding.rvBusStop.adapter = adapter
        binding.rvBusStop.layoutManager = LinearLayoutManager(requireContext(), VERTICAL, false)
    }
    private fun initAdapter() {
        adapter = BusStopForecastAdapter(childFragmentManager, seeBusLineInMap =
        { latLng , hour ->
            val place = Place(title = dateStringFormatter(hour) , latLng)
            val intent = Intent(requireContext(), MapsActivity::class.java)
            intent.putExtra("markersForTheMap",
                MarkerInGmaps(title = args.busStop.name,
                    listMarker = place.toList()
                ))
            startActivity(intent)
        })
    }
}