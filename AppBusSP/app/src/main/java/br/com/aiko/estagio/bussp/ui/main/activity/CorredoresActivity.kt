package br.com.aiko.estagio.bussp.ui.main.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.com.aiko.estagio.bussp.R
import br.com.aiko.estagio.bussp.databinding.ActivityCorredoresBinding
import br.com.aiko.estagio.bussp.databinding.HeaderNavigationDrawerBinding
import br.com.aiko.estagio.bussp.ui.main.MainActivity
import br.com.aiko.estagio.bussp.ui.main.adapter.CorredorAdapter
import br.com.aiko.estagio.bussp.ui.main.viewmodel.CorredorViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CorredoresActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCorredoresBinding
    private lateinit var corredorAdapter: CorredorAdapter

    private val corredorViewModel: CorredorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCorredoresBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupList()
        setupListener()
    }

    private fun setupList() {
        corredorAdapter = CorredorAdapter()
        binding.rvCorredores.adapter = corredorAdapter

        corredorViewModel.corredor.observe(this) { corredores ->
            Log.e("ssss", corredores.toString())
            corredorAdapter.submitList(corredores)
        }
        corredorViewModel.corredor()
    }

    private fun setupListener() {
        binding.topAppBar.setNavigationOnClickListener {
            binding.drawerLayoutCorredor.open()
        }

        /*
        *  DrawerLayout
        */
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            binding.drawerLayoutCorredor.close()
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
}