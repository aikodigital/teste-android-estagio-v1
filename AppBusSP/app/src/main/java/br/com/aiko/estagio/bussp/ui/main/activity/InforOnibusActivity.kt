package br.com.aiko.estagio.bussp.ui.main.activity

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.aiko.estagio.bussp.R
import br.com.aiko.estagio.bussp.data.remote.response.LinhasLocalizada
import br.com.aiko.estagio.bussp.data.remote.response.VeiculoLocalizado
import br.com.aiko.estagio.bussp.databinding.ActivityInforOnibusBinding
import br.com.aiko.estagio.bussp.ui.main.MainActivity.Companion.location
import br.com.aiko.estagio.bussp.ui.main.adapter.PrevisaoParadaAdapter
import br.com.aiko.estagio.bussp.ui.main.utils.dialogs.Dialogs
import br.com.aiko.estagio.bussp.ui.main.viewmodel.PosicaoViewModel
import br.com.aiko.estagio.bussp.ui.main.viewmodel.PrevisaoViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.Locale
import kotlin.properties.Delegates

@AndroidEntryPoint
class InforOnibusActivity : AppCompatActivity(), OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener {

    private lateinit var binding: ActivityInforOnibusBinding
    private lateinit var previsaoParadaAdapter: PrevisaoParadaAdapter

    private val posicaoViewModel: PosicaoViewModel by viewModels()
    private val previsaoViewModel: PrevisaoViewModel by viewModels()

    private lateinit var mMap: GoogleMap

    val map: MutableMap<VeiculoLocalizado, LinhasLocalizada> = mutableMapOf()

    private var codigoParada = -1
    private var codigoLinha by Delegates.notNull<Int>()
    private lateinit var minhaParada: LatLng

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInforOnibusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupIntentMethod()
        setupList()
        setupMap()
        setupListner()

        posicaoAutualizadaVeiculos()

    }

    private fun posicaoAutualizadaVeiculos() {
        CoroutineScope(Dispatchers.Main).launch {
            while (isActive) {
                delay(60000)
                previsaoLinha(codigoLinha)
            }
        }
    }

    private fun previsaoParada(codigoParada: Int) {
        val list: MutableList<Pair<VeiculoLocalizado, LinhasLocalizada>> = mutableListOf()

        previsaoViewModel.previsaoParada(codigoParada)
        previsaoViewModel.previsao.observe(this) { previsao ->
            previsao.p.l.forEach { linhas ->
                linhas.vs.forEach { veiculos ->
                    map[veiculos] = linhas
                }
            }

            list.addAll(map.toList())
            previsaoParadaAdapter.submitList(list)

            binding.tvNomeParada.text = "$codigoParada\n${previsao.p.np}"

        }
    }

    private fun previsaoLinha(codigoLinha: Int) {
        mMap.clear()

        mMap.addMarker(MarkerOptions().position(minhaParada).title("Ponto"))?.setIcon(
            BitmapDescriptorFactory.fromResource(R.drawable.ponto_de_onibus)
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(minhaParada, 10f))
        mMap.addMarker(MarkerOptions().position(location).title("Eu"))?.setIcon(
            BitmapDescriptorFactory.fromResource(R.drawable.posicao)
        )

        previsaoViewModel.previsaoLinha.observe(this) { linha ->
            linha.ps.forEach { l ->
                l.vs.forEach { v ->
                    val location = LatLng(v.py, v.px)
                    mMap.addMarker(MarkerOptions().position(location).title("${v.p}:${v.t}"))
                        ?.setIcon(
                            BitmapDescriptorFactory.fromResource(R.drawable.onibus_loc)
                        )
                }
            }
        }
        previsaoViewModel.previsaoLinha(codigoLinha)
    }

    private fun setupIntentMethod() {
        codigoParada = intent.getIntExtra("parada", -1)
        Log.e("lllll", codigoParada.toString())
        if (codigoParada != -1) {
            previsaoParada(codigoParada)
        }
    }

    private fun setupListner() {
        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupList() {
        previsaoParadaAdapter = PrevisaoParadaAdapter { codigoLinha ->
            handleItemClick(codigoLinha)
        }
        binding.rvOnibus.adapter = previsaoParadaAdapter
        binding.rvOnibus.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun handleItemClick(linhaCodigo: Int) {
        // Atualize o mapa com o código da linha
        previsaoLinha(linhaCodigo)
        codigoLinha = linhaCodigo
    }


    /*
        Funções para construção e manipulação do mapa
    */

    private fun setupMap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapa) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0

        mMap.uiSettings.isRotateGesturesEnabled = false
        mMap.uiSettings.isTiltGesturesEnabled = false
        mMap.isBuildingsEnabled = false
        mMap.isTrafficEnabled = false
        mMap.uiSettings.isMapToolbarEnabled = false


        mMap.addMarker(MarkerOptions().position(location).title("Eu"))?.setIcon(
            BitmapDescriptorFactory.fromResource(R.drawable.posicao)
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10f))


        previsaoViewModel.previsao.observe(this) { previsao ->
            minhaParada = LatLng(previsao.p.py, previsao.p.px)
            mMap.addMarker(MarkerOptions().position(minhaParada).title("Ponto"))?.setIcon(
                BitmapDescriptorFactory.fromResource(R.drawable.ponto_de_onibus)
            )
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(minhaParada, 10f))
        }

        binding.btnPos.setOnClickListener {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10f))
        }

    }


    override fun onMarkerClick(p0: Marker): Boolean {
        performReverseGeocoding(p0.position)
        return true
    }

    private fun performReverseGeocoding(latlng: LatLng) {
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            val addresses: List<Address>? =
                geocoder.getFromLocation(latlng.latitude, latlng.longitude, 1)
            if (!addresses.isNullOrEmpty()) {
                val address: Address = addresses[0]
                val streetName = address.subLocality
                if (streetName != null) {
                    Log.d("performReverseGeocoding", "$streetName")
                } else {
                    Log.d("performReverseGeocoding", "Rua não encontrada")
                }
            } else {
                Dialogs.showErrorMaterialDialog("Nenhum endereço encontrado", this)
            }
        } catch (e: IOException) {
            Log.e("performReverseGeocoding exception", e.message.toString())
        }
    }

}