package br.com.aiko.estagio.bussp.ui.main

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.viewModelScope
import br.com.aiko.estagio.bussp.R
import br.com.aiko.estagio.bussp.databinding.ActivityMainBinding
import br.com.aiko.estagio.bussp.ui.main.viewmodel.ParadasViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    private val paradasViewModel: ParadasViewModel by viewModels()

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


//        viewModel.buscarLinha.observe(this) { linhas ->
//            var res = ""
//            linhas.forEach { l ->
//                res += "$l \n"
//            }
//            binding.tvLinhas.text = res
//        }
//        viewModel.buscarLinha("Lapa")

//        viewModel.buscarLinhaSentido.observe(this) { linhas ->
//            binding.tvLinhas.text = linhas.toString()
//        }
//        viewModel.buscarLinhaSentido("Lapa", 1)


//        paradaViewModel.paradas.observe(this) { paradas ->
//            Log.e("paradaViewModel", paradas.toString())
//            binding.tvLinhas.text = paradas.toString()
//        }
//        paradaViewModel.buscarParada("Afonso")

        paradasViewModel.paradas.observe(this){ paradas ->
            binding.tvLinhas.text = paradas.toString()
        }
        paradasViewModel.buscarParadasPorLinha("34041")
    }
}