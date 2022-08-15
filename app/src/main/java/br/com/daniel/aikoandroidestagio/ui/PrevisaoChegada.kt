package br.com.daniel.aikoandroidestagio.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.com.daniel.aikoandroidestagio.R
import br.com.daniel.aikoandroidestagio.databinding.ActivityPrevisaoChegadaBinding
import br.com.daniel.aikoandroidestagio.model.Parada
import br.com.daniel.aikoandroidestagio.model.PrevisaoChegada
import br.com.daniel.aikoandroidestagio.services.ApiModule
import br.com.daniel.aikoandroidestagio.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class PrevisaoChegada : AppCompatActivity() {
    private val binding by lazy { ActivityPrevisaoChegadaBinding.inflate(LayoutInflater.from(this)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val parada = intent.getSerializableExtra(Constants.parada) as Parada

        var previsaoChegada: PrevisaoChegada? = null
        lifecycleScope.launch {
            val resposta = ApiModule.getPrevisao(parada.id)
            if (resposta.isSuccessful) {
                previsaoChegada = resposta.body()
            } else {
                Toast.makeText(this@PrevisaoChegada, getString(R.string.algo_errado), Toast.LENGTH_LONG).show()
                finish()
            }
        }

        previsaoChegada?.let { prev ->

        }
    }
}