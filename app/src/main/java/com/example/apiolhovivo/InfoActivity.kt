package com.example.apiolhovivo

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import classes.Parada
import classes.Previsao
import classes.VeiculoPrevisao
import classes.adapterInfo
import classes.getParada
import classes.getPrevisao
import classes.validation
import com.example.apiolhovivo.databinding.ActivityInfoBinding
import kotlinx.coroutines.launch

class InfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_info)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val txt_nomeParada: TextView = findViewById(R.id.txt_nomeParada)
        val txt_enderecoParada: TextView = findViewById(R.id.txt_endereco)
        val txt_aviso: TextView = findViewById(R.id.txt_noResult)

        val nomeParada = intent.getStringExtra("nomeParada").toString()
        val enderecoParada = intent.getStringExtra("enderecoParada").toString()
        val codigoLinha = intent.getStringExtra("codigoLinha").toString()
        val codigoParada = intent.getStringExtra("codigoParada").toString()

        txt_nomeParada.setText(nomeParada)
        txt_enderecoParada.setText(enderecoParada)

        lifecycleScope.launch {
            try {
                val data = validation()

                println(data)

                val previsao: Previsao = getPrevisao(codigoParada, codigoLinha)

                if (previsao.p.l[0].vs.size > 0) initRecyclerView(previsao.p.l[0].vs)
                txt_aviso.visibility = View.INVISIBLE
            } catch (e: Exception) {
                txt_aviso.visibility = View.VISIBLE
            }
        }


    }

    private fun initRecyclerView(lista: List<VeiculoPrevisao>) {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapterInfo(lista)
    }


}