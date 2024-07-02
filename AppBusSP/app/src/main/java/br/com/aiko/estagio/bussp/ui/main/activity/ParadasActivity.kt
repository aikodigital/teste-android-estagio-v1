package br.com.aiko.estagio.bussp.ui.main.activity

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isNotEmpty
import br.com.aiko.estagio.bussp.R
import br.com.aiko.estagio.bussp.data.remote.response.Parada
import br.com.aiko.estagio.bussp.databinding.ActivityParadasBinding
import br.com.aiko.estagio.bussp.ui.main.MainActivity.Companion.location
import br.com.aiko.estagio.bussp.ui.main.adapter.ParadasAdapter
import br.com.aiko.estagio.bussp.ui.main.viewmodel.AuthenticationViewModel
import br.com.aiko.estagio.bussp.ui.main.viewmodel.ParadasViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.util.Locale

@AndroidEntryPoint
class ParadasActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var binding: ActivityParadasBinding
    private lateinit var paradasAdapter: ParadasAdapter

    private val authenticationViewModel: AuthenticationViewModel by viewModels()
    private val paradasViewModel: ParadasViewModel by viewModels()

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParadasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        autenticacaoSetup()

        setupFiltro()
        setupIntentMethod()
        setupList()

        setupListener()
        setupMap()
    }

    private fun autenticacaoSetup() {
        authenticationViewModel.authentication("5f13bb5bf9366a7a349edf57a769e47421e0d8e9765a307ebb1243bf782dd6b4")
    }

    private fun setupListener() {
        binding.btnPesquisarParada.setOnClickListener {

            val parada = binding.tilParadaCampo.editText?.text.toString()
            val filtro = binding.tilFiltroParada.editText?.text.toString()

            Log.d("setuplistener", "$parada $filtro")

            if (parada.isNotEmpty() && filtro.isNotEmpty()) {
                when (filtro) {
                    "Nome ou endereço" -> setupParadas(parada)

                    "Código" -> setupParadasPorLinhas(parada.toInt())

                    "Corredor" -> setupParadasPorCorredor(parada.toInt())
                }
            } else {
                Log.e("errror", "$parada $filtro")
            }
        }
    }

    private fun setupIntentMethod() {
        val cl = intent.getIntExtra("cl", -1)

        if (cl != -1) {
            setupParadasPorLinhas(cl).toString()
            binding.tilParadaCampo.hint = "Código"
            binding.tilParadaCampo.editText?.setText(cl.toString())
            binding.tilFiltroParada.editText?.hint = "Código"
            binding.tvParadas.text = "Paradas por código da linha"
        }

    }

    private fun setupParadas(parada: String) {
        paradasViewModel.buscarParada(parada)
        paradasViewModel.paradas.observe(this) { paradas ->
            paradasAdapter.submitList(paradas)
        }
    }

    private fun setupParadasPorLinhas(codigoLinha: Int) {
        paradasViewModel.buscarParadasPorLinha(codigoLinha.toString())
        paradasViewModel.paradas.observe(this) { paradas ->
            paradasAdapter.submitList(paradas)
        }
    }

    private fun setupParadasPorCorredor(codigoCorredor: Int) {
        paradasViewModel.buscarParadasPorCorredor(codigoCorredor)
        paradasViewModel.paradas.observe(this) { paradas ->
            paradasAdapter.submitList(paradas)
        }
    }


    private fun setupFiltro() {
        val itens = listOf("Nome ou endereço", "Código", "Corredor")
        val filtroAdapter = ArrayAdapter(this, R.layout.filtro_item_list, itens)
        (binding.tilFiltroParada.editText as? AutoCompleteTextView)?.setAdapter(filtroAdapter)
    }

    private fun setupList() {
        paradasAdapter = ParadasAdapter(this)
        binding.rvParadas.adapter = paradasAdapter
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

        paradasViewModel.paradas.observe(this) { paradas ->
            paradas.forEach {
                mMap.addMarker(MarkerOptions().position(LatLng(it.py, it.px)).title(it.ed))
                    ?.setIcon(
                        BitmapDescriptorFactory.fromResource(R.drawable.ponto_de_onibus)
                    )
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(it.py, it.px), 10f))
            }
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
                Log.e("performReverseGeocoding", "Nenhum endereço encontrado")
            }
        } catch (e: IOException) {
            Log.e("performReverseGeocoding exception", e.message.toString())
        }
    }


}