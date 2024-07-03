package br.dev.saed.saedrastreamentosapi.activities

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.dev.saed.saedrastreamentosapi.R
import br.dev.saed.saedrastreamentosapi.adapters.recyclerview.PrevisaoAdapter
import br.dev.saed.saedrastreamentosapi.apis.OlhoVivoAPI
import br.dev.saed.saedrastreamentosapi.apis.RetrofitHelper
import br.dev.saed.saedrastreamentosapi.databinding.ActivityVeiculosPrevisaoBinding
import br.dev.saed.saedrastreamentosapi.models.Parada
import br.dev.saed.saedrastreamentosapi.models.VeiculosPrevisao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class VeiculosPrevisaoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVeiculosPrevisaoBinding
    private lateinit var retrofit: Retrofit
    private lateinit var previsaoAdapter: PrevisaoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVeiculosPrevisaoBinding.inflate(layoutInflater)
        retrofit = RetrofitHelper.retrofit
        setContentView(binding.root)
        inicializarComponentes()
        carregarRV()
    }

    private fun inicializarComponentes() {
        binding.fabInformacoesPrevisao.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this)
            alertDialog
                .setTitle("Previsões")
                .setMessage(R.string.text_info_previsoes)
                .setPositiveButton("OK") { _, _ -> }
                .create()
                .show()
        }

        val extras = intent.extras
        if (extras != null) {
            val parada = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                extras.getParcelable("parada", Parada::class.java)
            } else {
                extras.getParcelable("parada")
            }
            if (parada != null) {
                binding.textPrevisaoParada.text = parada.title.ifBlank { "PARADA SEM NOME" }
                popularRV(parada.cp)
            }
        }
    }

    private fun popularRV(codigo: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val listaVeiculos = async { buscarPrevisaoParadas(codigo) }.await()
            withContext(Dispatchers.Main) {
                previsaoAdapter.atualizar(listaVeiculos)
            }
        }
    }

    private fun carregarRV() {
        previsaoAdapter = PrevisaoAdapter()
        binding.rvPrevisao.adapter = previsaoAdapter
        binding.rvPrevisao.layoutManager =
            LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
    }

    private suspend fun buscarPrevisaoParadas(codigo: Int): List<VeiculosPrevisao> {
        val previsoes = mutableListOf<VeiculosPrevisao>()
        try {
            val resultado = retrofit.create(OlhoVivoAPI::class.java).buscarPrevisaoParadas(codigo)
            if (resultado.isSuccessful) {
                val previsao = resultado.body()
                previsao?.p?.l?.forEach { linhasPrevisao ->
                    linhasPrevisao.vs.forEach { veiculosPrevisao ->
                        previsoes.add(veiculosPrevisao)
                    }
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(applicationContext, R.string.text_no_internet, Toast.LENGTH_LONG)
                    .show()
            }
        }
        return previsoes
    }
}