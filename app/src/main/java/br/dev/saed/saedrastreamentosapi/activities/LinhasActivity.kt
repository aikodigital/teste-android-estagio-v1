package br.dev.saed.saedrastreamentosapi.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.dev.saed.saedrastreamentosapi.R
import br.dev.saed.saedrastreamentosapi.adapters.recyclerview.LinhaAdapter
import br.dev.saed.saedrastreamentosapi.apis.OlhoVivoAPI
import br.dev.saed.saedrastreamentosapi.apis.RetrofitHelper
import br.dev.saed.saedrastreamentosapi.databinding.ActivityLinhasBinding
import br.dev.saed.saedrastreamentosapi.models.Linha
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class LinhasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLinhasBinding
    private lateinit var retrofit: Retrofit
    private lateinit var linhaAdapter: LinhaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLinhasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        retrofit = RetrofitHelper.retrofit
        inicializarComponentes()
        carregarRV()
    }

    private fun inicializarComponentes() {
        binding.buttonProcurarLinhas.setOnClickListener {
            val texto = binding.editTextLinha.text.toString()
            if (texto.isEmpty()) {
                binding.editTextLinha.error = "Obrigatório!"
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    popularRV(texto, linhaAdapter)
                }
            }
        }

        binding.fabInformacoesLinhas.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this)
            alertDialog
                .setTitle("Linhas")
                .setMessage(R.string.text_info_linhas)
                .setPositiveButton("OK") { _, _ -> }
                .create()
                .show()
        }
    }

    private fun carregarRV() {
        linhaAdapter = LinhaAdapter()
        binding.rvLinhas.adapter = linhaAdapter
        binding.rvLinhas.layoutManager =
            LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
    }

    private fun popularRV(texto: String, linhaAdapter: LinhaAdapter) {
        CoroutineScope(Dispatchers.IO).launch {
            val listaLinhas = async {
                buscarLinhas(texto)
            }.await()
            withContext(Dispatchers.Main) {
                linhaAdapter.atualizar(listaLinhas)
            }
        }
    }

    private suspend fun buscarLinhas(texto: String): List<Linha> {
        val linhas = mutableListOf<Linha>()

        try {
            val resultado = retrofit.create(OlhoVivoAPI::class.java).buscarLinhas(texto)
            if (resultado.isSuccessful) {
                val linha = resultado.body()
                linha?.forEach {
                    linhas.add(it)
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
        return linhas
    }
}