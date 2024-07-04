package br.com.okayamafilho.testesptrans.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.com.okayamafilho.testesptrans.R
import br.com.okayamafilho.testesptrans.data.RetrofitClient.spTransAPI
import br.com.okayamafilho.testesptrans.databinding.FragmentHomeBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var googleMaps: GoogleMap
    private var job: Job? = null
    private var job1: Job? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val root: View = binding.root

        binding.buttonPesquisa.setOnClickListener {
            val editTextPesquisa = binding.editTextPesquisa.text.toString()
            getStopBusLine(editTextPesquisa)
        }

        binding.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                getPositionBus("2510")
            } else {
                getStopBusLine("8000")
            }
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(readyGoogleMap: GoogleMap) {
        googleMaps = readyGoogleMap
        getStopBusLine("8000")
    }

    private fun getStopBusLine(texto: String) {
        job = CoroutineScope(Dispatchers.IO).launch {
            try {
                val authResponse =
                    spTransAPI.authResponse("")

                val headers = authResponse.headers().values("Set-Cookie")
                val cookie = headers.joinToString("; ")

                val searchStopResponse =
                    spTransAPI.searchStopBus(cookie = cookie, termosBusca = texto)
                withContext(Dispatchers.Main) {
                    searchStopResponse.forEach { lineStop ->
                        googleMaps.addMarker(
                            MarkerOptions().position(
                                LatLng(
                                    lineStop.py,
                                    lineStop.px
                                )
                            ).title(lineStop.np)
                        )
                    }
                    val lastStopLine = searchStopResponse.first()
                    googleMaps.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(lastStopLine.py, lastStopLine.px),
                            10F
                        )
                    )
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun getPositionBus(texto: String) {
        job1 = CoroutineScope(Dispatchers.IO).launch {
            try {
                val authResponse =
                    spTransAPI.authResponse("")

                val headers = authResponse.headers().values("Set-Cookie")
                val cookie = headers.joinToString("; ")

                val searchStopResponse = spTransAPI.searchPositionBus(cookie = cookie, texto)
                if (searchStopResponse.vs.isNotEmpty()) {
                    withContext(Dispatchers.Main) {
                        val listaPositionBus = searchStopResponse.vs

                        listaPositionBus.forEach { positionBus ->
                            googleMaps.addMarker(
                                MarkerOptions().position(
                                    LatLng(
                                        positionBus.py,
                                        positionBus.px
                                    )
                                )
                            )
                        }
                        val lastPositionBus = listaPositionBus.first()
                        googleMaps.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                LatLng(
                                    lastPositionBus.py,
                                    lastPositionBus.px
                                ), 18F
                            )
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        job?.cancel()
        job1?.cancel()
    }
}