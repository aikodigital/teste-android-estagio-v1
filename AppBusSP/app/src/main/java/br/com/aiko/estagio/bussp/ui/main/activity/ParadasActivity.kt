package br.com.aiko.estagio.bussp.ui.main.activity

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.aiko.estagio.bussp.R
import br.com.aiko.estagio.bussp.databinding.ActivityParadasBinding
import br.com.aiko.estagio.bussp.ui.main.MainActivity.Companion.location
import br.com.aiko.estagio.bussp.ui.main.adapter.ParadasAdapter
import br.com.aiko.estagio.bussp.ui.main.utils.dialogs.Dialogs
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

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        autenticacaoSetup()
        setupFiltro()
        setupIntentMethod()
        setupList()
        setupListener()
        setupMap()
    }

    private fun autenticacaoSetup() {
        if (isConectado(this)) {
            authenticationViewModel.authentication("5f13bb5bf9366a7a349edf57a769e47421e0d8e9765a307ebb1243bf782dd6b4")
        } else {
            Dialogs.showErrorMaterialDialog("Sem internet", this)
        }
    }

    private fun setupListener() {
        binding.btnPesquisarParada.setOnClickListener {

            val parada = binding.tilParadaCampo.editText?.text.toString()
            val filtro = binding.tilFiltroParada.editText?.text.toString()

            if (parada.isNotEmpty() && filtro.isNotEmpty()) {
                when (filtro) {
                    "Nome" -> {
                        setupParadas(parada)
                    }

                    "Endereço" -> {
                        setupParadas(parada)
                    }

                    "Código" -> {
                        setupParadasPorLinhas(parada.toInt())
                    }

                    "Corredor" -> {
                        setupParadasPorCorredor(parada.toInt())
                    }
                }
            } else {
                Dialogs.showErrorMaterialDialog(
                    "Os campos \"Pesquisa por\" e \"Filtro\" devem ser preechidos.",
                    this
                )
                Log.e("errror", "$parada $filtro")
            }
        }

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupIntentMethod() {
        val cl = intent.getIntExtra("cl", -1)

        if (cl != -1) {
            setupParadasPorLinhas(cl).toString()
            binding.tilParadaCampo.editText?.setText(cl.toString())
        }

    }

    private fun setupParadas(parada: String) {
        if (isConectado(this)) {
            paradasViewModel.buscarParada(parada)
            paradasViewModel.paradas.observe(this) { paradas ->
                paradasAdapter.submitList(paradas)
            }
        } else {
            Dialogs.showErrorMaterialDialog("Sem internet", this)
        }
    }

    private fun setupParadasPorLinhas(codigoLinha: Int) {
        if (isConectado(this)) {
            paradasViewModel.buscarParadasPorLinha(codigoLinha.toString())
            paradasViewModel.paradas.observe(this) { paradas ->
                paradasAdapter.submitList(paradas)
            }
        } else {
            Dialogs.showErrorMaterialDialog("Sem internet", this)
        }
    }

    private fun setupParadasPorCorredor(codigoCorredor: Int) {
        if (isConectado(this)) {
            paradasViewModel.buscarParadasPorCorredor(codigoCorredor)
            paradasViewModel.paradas.observe(this) { paradas ->
                paradasAdapter.submitList(paradas)
            }
        } else {
            Dialogs.showErrorMaterialDialog("Sem internet", this)
        }
    }

    private fun setupFiltro() {
        val itens = listOf("Nome", "Endereço", "Código", "Corredor")
        val filtroAdapter = ArrayAdapter(this, R.layout.filtro_item_list, itens)
        (binding.tilFiltroParada.editText as? AutoCompleteTextView)?.setAdapter(filtroAdapter)
    }

    private fun setupList() {
        paradasAdapter = ParadasAdapter(this)
        binding.rvParadas.adapter = paradasAdapter
        binding.rvParadas.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun isConectado(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
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

            mMap.clear()

            mMap.addMarker(MarkerOptions().position(location).title("Eu"))?.setIcon(
                BitmapDescriptorFactory.fromResource(R.drawable.posicao)
            )
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10f))

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
                Dialogs.showErrorMaterialDialog("Nenhum endereço encontrado", this)
                //Log.e("performReverseGeocoding", "Nenhum endereço encontrado")
            }
        } catch (e: IOException) {
            Log.e("performReverseGeocoding exception", e.message.toString())
        }
    }


}