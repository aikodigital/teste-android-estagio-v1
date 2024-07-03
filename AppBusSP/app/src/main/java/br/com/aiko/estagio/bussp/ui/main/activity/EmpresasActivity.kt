package br.com.aiko.estagio.bussp.ui.main.activity

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.aiko.estagio.bussp.R
import br.com.aiko.estagio.bussp.data.remote.response.Empresa
import br.com.aiko.estagio.bussp.data.remote.response.EmpresasAreaOcupcao
import br.com.aiko.estagio.bussp.databinding.ActivityEmpresasBinding
import br.com.aiko.estagio.bussp.databinding.HeaderNavigationDrawerBinding
import br.com.aiko.estagio.bussp.ui.main.MainActivity
import br.com.aiko.estagio.bussp.ui.main.adapter.EmpresaAdapter
import br.com.aiko.estagio.bussp.ui.main.utils.dialogs.Dialogs
import br.com.aiko.estagio.bussp.ui.main.viewmodel.AuthenticationViewModel
import br.com.aiko.estagio.bussp.ui.main.viewmodel.EmpresaViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmpresasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmpresasBinding
    private lateinit var empresaAdapter: EmpresaAdapter

    private val authenticationViewModel: AuthenticationViewModel by viewModels()
    private val empresaViewModel: EmpresaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmpresasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupList()
        setupListener()
    }

    private fun autenticacaoSetup() {
        if (isConectado(this)) {
            authenticationViewModel.authentication("5f13bb5bf9366a7a349edf57a769e47421e0d8e9765a307ebb1243bf782dd6b4")
        } else {
            Dialogs.showErrorMaterialDialog("Sem internet", this)
        }
    }

    private fun buscarEmpresas() {
        var map: MutableMap<Empresa, EmpresasAreaOcupcao> = mutableMapOf()
        var list: MutableList<Pair<Empresa, EmpresasAreaOcupcao>> = mutableListOf()

        if (isConectado(this)) {
            empresaViewModel.empresas.observe(this) { empresas ->
                /*
            * Buscar pelas empresas e sua área de ocupação, adicionando ao map mutável,
            * permitindo retornar todas as empresas sem perder a área de ocupação. Depois
            * converte para uma lista de par mutável, onde o par (Empresa, Area) é passado ao
            * adapter.
            */
                empresas.e.forEach { area ->
                    area.e.forEach { empresa ->
                        map[empresa] = area
                    }
                }
                list.addAll(map.toList())
                empresaAdapter.submitList(list)
            }
            empresaViewModel.empresas()
        } else {
            Dialogs.showErrorMaterialDialog("Sem internet", this)
        }
    }

    private fun setupList() {
        empresaAdapter = EmpresaAdapter()
        binding.rvEmpresas.adapter = empresaAdapter
        binding.rvEmpresas.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        buscarEmpresas()
    }

    private fun setupListener() {
        binding.topAppBar.setNavigationOnClickListener {
            binding.drawerLayoutEmpresa.open()
        }

        /*
        *  DrawerLayout
        */
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            binding.drawerLayoutEmpresa.close()
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

}