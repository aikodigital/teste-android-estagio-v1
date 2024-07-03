package br.com.aiko.estagio.bussp.ui.main

import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.com.aiko.estagio.bussp.databinding.ActivityMainBinding
import br.com.aiko.estagio.bussp.databinding.HeaderNavigationDrawerBinding
import br.com.aiko.estagio.bussp.ui.main.activity.CorredoresActivity
import br.com.aiko.estagio.bussp.ui.main.activity.EmpresasActivity
import br.com.aiko.estagio.bussp.ui.main.activity.ParadasActivity
import br.com.aiko.estagio.bussp.ui.main.adapter.LinhasAdapter
import br.com.aiko.estagio.bussp.ui.main.utils.dialogs.Dialogs
import br.com.aiko.estagio.bussp.ui.main.viewmodel.AuthenticationViewModel
import br.com.aiko.estagio.bussp.ui.main.viewmodel.BuscarLinhaViewModel
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.util.Locale

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val authenticationViewModel: AuthenticationViewModel by viewModels()
    private val buscarLinhaViewModel: BuscarLinhaViewModel by viewModels()

    private lateinit var linhasAdapter: LinhasAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        autenticacaoSetup()
        setupList()
        setupListener()
        buscarLinhaProximidades()
    }

    private fun autenticacaoSetup() {
        if (isConectado(this)) {
            authenticationViewModel.authentication(
                "5f13bb5bf9366a7a349edf57a769e47421e0d8e9765a307ebb1243bf782dd6b4"
            )
        } else {
            Dialogs.showErrorMaterialDialog("Sem internet", this)
        }
    }

    private fun buscarLinhaProximidades() {
        if (isConectado(this)) {
            buscarLinhaViewModel.buscarLinha.observe(this) { linhas ->
                linhasAdapter.submitList(linhas)
            }
            buscarLinhaViewModel.buscarLinha(performReverseGeocoding(location))
        } else {
            Dialogs.showErrorMaterialDialog("Sem internet", this)
        }
    }

    private fun buscarLinhaPorSentido(linha: String, sentido: Int) {
        if (isConectado(this)) {

            buscarLinhaViewModel.buscarLinhaSentido.observe(this) { linha ->
                linhasAdapter.submitList(linha)
            }
            buscarLinhaViewModel.buscarLinhaSentido(linha, sentido)
        } else {
            Dialogs.showErrorMaterialDialog("Sem internet", this)
        }
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

    private fun setupList() {
        linhasAdapter = LinhasAdapter(this)
        binding.rvLinhasSugeridas.adapter = linhasAdapter
    }

    private fun setupListener() {
        binding.btnSentidoTs.setOnClickListener {
            val linha = binding.tilOrigem.editText?.text.toString().trim()

            if (linha.isNotEmpty()) {
                binding.tvLinhasProximas.text = "Linhas no sentido Terminal Secundário"

                buscarLinhaPorSentido(linha, 2)
            } else {
                Dialogs.showErrorMaterialDialog("Preecha os campos de pesquisa", this)
            }
        }

        binding.btnSentidoTp.setOnClickListener {
            val linha = binding.tilOrigem.editText?.text.toString().trim()

            if (linha.isNotEmpty()) {
                binding.tvLinhasProximas.text = "Linhas no sentido Terminal Principal"
                buscarLinhaPorSentido(linha, 1)
            } else {
                Dialogs.showErrorMaterialDialog("Preecha os campos de pesquisa", this)
            }
        }

        binding.topAppBar.setNavigationOnClickListener {
            binding.main.open()
        }

        /*
        *  DrawerLayout
        */
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            binding.main.close()
            true
        }

        val navigationHeaderBinding = HeaderNavigationDrawerBinding.bind(
            binding.navigationView.getHeaderView(0)
        )
        navigationHeaderBinding.tvLinhas.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        navigationHeaderBinding.tvParadas.setOnClickListener {
            val intent = Intent(this, ParadasActivity::class.java)
            startActivity(intent)
        }
        navigationHeaderBinding.tvCorredores.setOnClickListener {
            val intent = Intent(this, CorredoresActivity::class.java)
            startActivity(intent)
            finish()
        }
        navigationHeaderBinding.tvEmpresas.setOnClickListener {
            val intent = Intent(this, EmpresasActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    // Função para recuperar o Bairro a partir da latitude e longitude
    private fun performReverseGeocoding(latlng: LatLng): String {
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            val addresses: List<Address> =
                geocoder.getFromLocation(latlng.latitude, latlng.longitude, 1)!!
            if (addresses.isNotEmpty()) {
                val address: Address = addresses[0]
                val addressText = address.subLocality
                return addressText
            } else {
                return ""
                Dialogs.showErrorMaterialDialog("Nenhum endereço encontrado", this)
            }
        } catch (e: IOException) {
            return ""
            //Log.e("performReverseGeocoding exception", e.message.toString())
        }
    }

    companion object {
        // Alto da Lapa
        val location = LatLng(-23.5354, -46.7178)
    }

}

