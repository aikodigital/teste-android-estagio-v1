package br.dev.saed.saedrastreamentosapi.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.dev.saed.saedrastreamentosapi.R
import br.dev.saed.saedrastreamentosapi.adapters.recyclerview.CorredorAdapter
import br.dev.saed.saedrastreamentosapi.apis.OlhoVivoAPI
import br.dev.saed.saedrastreamentosapi.apis.RetrofitHelper
import br.dev.saed.saedrastreamentosapi.databinding.ActivityCorredorBinding
import br.dev.saed.saedrastreamentosapi.models.Corredor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class CorredoresActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCorredorBinding
    private lateinit var retrofit: Retrofit
    private lateinit var corredorAdapter: CorredorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCorredorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        retrofit = RetrofitHelper.retrofit
        carregarRV()
        inicializarComponentes()
    }

    private fun inicializarComponentes() {
        popularRV(corredorAdapter)

        binding.fabInformacoesCorredores.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this)
            alertDialog
                .setTitle("Corredores")
                .setMessage(R.string.text_info_corredores)
                .setPositiveButton("OK") { _, _ -> }
                .create()
                .show()
        }
    }

    private fun carregarRV() {
        corredorAdapter = CorredorAdapter()
        binding.rvCorredores.adapter = corredorAdapter
        binding.rvCorredores.layoutManager =
            LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
    }

    private fun popularRV(corredorAdapter: CorredorAdapter) {
        CoroutineScope(Dispatchers.IO).launch {
            val listaCorredores = async {
                buscarCorredores()
            }.await()
            withContext(Dispatchers.Main) {
                corredorAdapter.atualizar(listaCorredores)
            }
        }
    }

    private suspend fun buscarCorredores() : List<Corredor> {
        try {
            val resultado = retrofit.create(OlhoVivoAPI::class.java).buscarCorredores()
            if (resultado.isSuccessful) {
                val corredores = resultado.body()
                if (corredores != null) {
                    val sortedList = corredores.sortedBy { it.cc }
                    return sortedList
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
        return listOf()
    }
}