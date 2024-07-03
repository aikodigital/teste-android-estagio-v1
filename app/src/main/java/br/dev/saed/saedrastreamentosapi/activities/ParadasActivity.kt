package br.dev.saed.saedrastreamentosapi.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.dev.saed.saedrastreamentosapi.R
import br.dev.saed.saedrastreamentosapi.adapters.recyclerview.ParadaAdapter
import br.dev.saed.saedrastreamentosapi.apis.OlhoVivoAPI
import br.dev.saed.saedrastreamentosapi.apis.RetrofitHelper
import br.dev.saed.saedrastreamentosapi.databinding.ActivityParadasBinding
import br.dev.saed.saedrastreamentosapi.models.Parada
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class ParadasActivity : AppCompatActivity() {
    private lateinit var binding: ActivityParadasBinding
    private lateinit var retrofit: Retrofit
    private lateinit var paradaAdapter: ParadaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParadasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        retrofit = RetrofitHelper.retrofit
        inicializarComponentes()
        carregarRV()
    }

    private fun inicializarComponentes() {
        binding.buttonProcurarParada.setOnClickListener {
            val texto = binding.editTextParada.text.toString()
            CoroutineScope(Dispatchers.IO).launch {
                popularRV(texto, paradaAdapter)
            }
        }

        binding.fabInformacoesParada.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this)
            alertDialog
                .setTitle("Paradas")
                .setMessage(R.string.text_info_paradas)
                .setPositiveButton("OK") { _, _ -> }
                .create()
                .show()
        }
    }

    private fun carregarRV() {
        paradaAdapter = ParadaAdapter(
            cliqueOnibus = { parada ->
                val intent = Intent(this, VeiculosPrevisaoActivity::class.java)
                intent.putExtra("parada", parada)
                startActivity(intent)
            },
            cliqueMapa = { parada ->
                val intent = Intent(this, MapsActivity::class.java)
                intent.putExtra("parada", parada)
                startActivity(intent)
            }
        )
        binding.rvParadas.adapter = paradaAdapter
        binding.rvParadas.layoutManager =
            LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
    }

    private fun popularRV(texto: String, paradaAdapter: ParadaAdapter) {
        CoroutineScope(Dispatchers.IO).launch {
            val listaParadas = async {
                buscarParadas(texto)
            }.await()
            withContext(Dispatchers.Main) {
                paradaAdapter.atualizar(listaParadas)
            }
        }
    }

    private suspend fun buscarParadas(texto: String): List<Parada> {
        val paradas = mutableListOf<Parada>()
        try {
            val resultado = retrofit.create(OlhoVivoAPI::class.java).buscarParadas(texto)
            if (resultado.isSuccessful) {
                val parada = resultado.body()
                parada?.forEach {
                    paradas.add(it)
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(applicationContext, R.string.text_no_internet, Toast.LENGTH_LONG)
                    .show()
            }
        }
        paradas.sortBy { it.np }
        return paradas
    }

}