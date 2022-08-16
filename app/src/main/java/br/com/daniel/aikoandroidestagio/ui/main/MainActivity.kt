package br.com.daniel.aikoandroidestagio.ui.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import br.com.daniel.aikoandroidestagio.R
import br.com.daniel.aikoandroidestagio.databinding.ActivityMainBinding
import br.com.daniel.aikoandroidestagio.services.ApiModule
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val TAG = "DEBUG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        lifecycleScope.launch(IO) {
            try {
                val resposta = ApiModule.autenticar()
                val biscoitinho = resposta.headers().get("Set-Cookie")
                biscoitinho?.let {
                    ApiModule.setCookie(biscoitinho)
                } ?: Log.d(TAG, "Veio sem cookie")

                Log.d(TAG, "headers-debug: $biscoitinho")
                Log.d(TAG, "API autenticada")
            } catch (e: Exception) {
                Log.d(TAG, "API fora do ar: " + e.message)
            }
        }

        confNavView()
    }

    private fun confNavView() {
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_posicao, R.id.navigation_paradas))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}