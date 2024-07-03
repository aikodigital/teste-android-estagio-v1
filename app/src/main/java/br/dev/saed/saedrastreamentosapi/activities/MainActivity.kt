package br.dev.saed.saedrastreamentosapi.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.dev.saed.saedrastreamentosapi.R
import br.dev.saed.saedrastreamentosapi.apis.OlhoVivoAPI
import br.dev.saed.saedrastreamentosapi.apis.RetrofitHelper
import br.dev.saed.saedrastreamentosapi.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var retrofit: Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        retrofit = RetrofitHelper.retrofit
        CoroutineScope(Dispatchers.IO).launch {
            autenticar()
        }
        inicializarComponentes()
    }

    private fun inicializarComponentes() {
        binding.buttonMapa.setOnClickListener {
            intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }

        binding.buttonLinhas.setOnClickListener {
            intent = Intent(this, LinhasActivity::class.java)
            startActivity(intent)
        }

        binding.buttonParadas.setOnClickListener {
            intent = Intent(this, ParadasActivity::class.java)
            startActivity(intent)
        }

        binding.buttonCorredores.setOnClickListener {
            intent = Intent(this, CorredoresActivity::class.java)
            startActivity(intent)
        }

    }

    private suspend fun autenticar() {
        try {
            val resultado = retrofit.create(OlhoVivoAPI::class.java).autenticar()
            withContext(Dispatchers.Main) {
                if (resultado) {
                    Toast.makeText(applicationContext, "Conectado a API!", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Falha ao conectar a API!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    applicationContext,
                    R.string.text_no_internet,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}