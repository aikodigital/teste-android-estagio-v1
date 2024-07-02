package br.com.aiko.estagio.bussp.ui.main

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.viewModelScope
import androidx.viewpager2.widget.ViewPager2
import br.com.aiko.estagio.bussp.R
import br.com.aiko.estagio.bussp.databinding.ActivityMainBinding
import br.com.aiko.estagio.bussp.databinding.HeaderNavigationDrawerBinding
import br.com.aiko.estagio.bussp.ui.main.activity.CorredoresActivity
import br.com.aiko.estagio.bussp.ui.main.activity.EmpresasActivity
import br.com.aiko.estagio.bussp.ui.main.activity.ParadasActivity
import br.com.aiko.estagio.bussp.ui.main.adapter.ViewPagerAdapter
import br.com.aiko.estagio.bussp.ui.main.fragment.MapaFragment
import br.com.aiko.estagio.bussp.ui.main.viewmodel.AuthenticationViewModel
import br.com.aiko.estagio.bussp.ui.main.viewmodel.BuscarLinhaViewModel
import br.com.aiko.estagio.bussp.ui.main.viewmodel.CorredorViewModel
import br.com.aiko.estagio.bussp.ui.main.viewmodel.EmpresaViewModel
import br.com.aiko.estagio.bussp.ui.main.viewmodel.ParadasViewModel
import br.com.aiko.estagio.bussp.ui.main.viewmodel.PosicaoViewModel
import br.com.aiko.estagio.bussp.ui.main.viewmodel.PrevisaoViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView
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

        autenticacaoSetup()
        setupList()
        setupListener()

        buscarLinhaProximidades()
        buscarLinhaPorSentido()

    }


    private fun autenticacaoSetup() {
        authenticationViewModel.authentication("5f13bb5bf9366a7a349edf57a769e47421e0d8e9765a307ebb1243bf782dd6b4")
    }

    private fun buscarLinhaProximidades() {
        buscarLinhaViewModel.buscarLinha.observe(this) { linhas ->
            linhasAdapter.submitList(linhas)
        }
        buscarLinhaViewModel.buscarLinha(performReverseGeocoding(location))

    }

    private fun buscarLinhaPorSentido() {
        binding.btnSentidoTp.setOnClickListener {
            val linha = binding.tilOrigem.editText?.text.toString().trim()

            binding.tvLinhasProximas.text = "Linhas no sentido Terminal Principal"

            buscarLinhaViewModel.buscarLinhaSentido(linha, 1)
            buscarLinhaViewModel.buscarLinhaSentido.observe(this) { linha ->
                linhasAdapter.submitList(linha)
            }
        }

        binding.btnSentidoTs.setOnClickListener {
            val linha = binding.tilOrigem.editText?.text.toString().trim()

            binding.tvLinhasProximas.text = "Linhas no sentido Terminal Secundário"

            buscarLinhaViewModel.buscarLinhaSentido.observe(this) { linha ->
                linhasAdapter.submitList(linha)
            }
            buscarLinhaViewModel.buscarLinhaSentido(linha, 2)
        }

    }

    private fun setupList() {
        linhasAdapter = LinhasAdapter(this)
        binding.rvLinhasSugeridas.adapter = linhasAdapter
    }

    private fun setupListener() {
        binding.topAppBar.setNavigationOnClickListener {
            binding.main.open()
        }

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
            finish()
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

    private fun performReverseGeocoding(latlng: LatLng): String {
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            val addresses: List<Address> =
                geocoder.getFromLocation(latlng.latitude, latlng.longitude, 1)!!
            if (addresses.isNotEmpty()) {
                val address: Address = addresses[0]
                val addressText = address.subLocality
                return addressText
                Log.d("performReverseGeocoding", addressText)
            } else {
                return ""
                Log.e("", "Nenhum endereço encontrado")
            }
        } catch (e: IOException) {
            return ""
            Log.e("performReverseGeocoding exception", e.message.toString())
        }
    }

    companion object {
        val location = LatLng(-23.5354, -46.7178)
    }

}

