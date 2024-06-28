package br.com.aiko.estagio.bussp.ui.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.com.aiko.estagio.bussp.R
import br.com.aiko.estagio.bussp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.authentication.observe(this) { isAuthenticed ->
            if (isAuthenticed) {
                binding.tvTeste.text = "True"
            } else {
                binding.tvTeste.text = "False"
            }
        }

        viewModel.authentication("5f13bb5bf9366a7a349edf57a769e47421e0d8e9765a307ebb1243bf782dd6b4")
    }
}