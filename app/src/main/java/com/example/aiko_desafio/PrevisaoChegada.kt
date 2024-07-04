package com.example.aiko_desafio

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aiko_desafio.Adapter.AdpterChegada
import com.example.aiko_desafio.databinding.ActivityPrevisaoChegadaBinding
import com.example.aiko_desafio.objetos.Chegada
import com.example.aiko_desafio.objetos.linha_previsao
import com.example.aiko_desafio.objetos.parada_previsao
import com.example.aiko_desafio.objetos.veiculo_previsao
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@SuppressLint("StaticFieldLeak")
private lateinit var b: ActivityPrevisaoChegadaBinding

@SuppressLint("StaticFieldLeak")
private lateinit var adapterChegada: AdpterChegada
private val listaChegada: MutableList<Chegada> = mutableListOf()

class PrevisaoChegada : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityPrevisaoChegadaBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)

        val recyclerViewChegada = b.listaChegada
        recyclerViewChegada.layoutManager = LinearLayoutManager(this)
        recyclerViewChegada.setHasFixedSize(true)
        adapterChegada = AdpterChegada(this, listaChegada)
        recyclerViewChegada.adapter = adapterChegada


        b.bntVoltar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
        }

        b.bntPrevisao.setOnClickListener {
            val texto = b.editParadas.text.toString()
            func_previsao(texto)
        }
    }

    private fun func_previsao(parada: String) {
        val retrofit = retrofit().retrofit
        retrofit.getPrevisao(parada).enqueue(object : Callback<Chegada> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<Chegada>,
                response: Response<Chegada>,
            ) {
                if (response.isSuccessful) {
                    if (response.body() == null) {
                        Toast.makeText(
                            this@PrevisaoChegada,
                            "Linha invalida!",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        val chegadaList: List<Chegada> = listOf(response.body()!!)
                        chegadaList.let { paradas ->
                            Log.d("teste", paradas.toString())
                            listaChegada.clear()
                            paradas.forEach { parada: Chegada ->
                                if (parada.p == null) {
                                    Toast.makeText(
                                        this@PrevisaoChegada,
                                        "Parada invalida!!",
                                        Toast.LENGTH_LONG
                                    ).show()

                                } else {
                                    for (linha in parada.p.l) {
                                        for (veiculo in linha.vs) {
                                            listaChegada.add(
                                                Chegada(
                                                    parada.hr,
                                                    parada_previsao(
                                                        parada.p.cp,
                                                        parada.p.np,
                                                        parada.p.py,
                                                        parada.p.px,
                                                        listOf(
                                                            linha_previsao(
                                                                linha.c,
                                                                linha.cl,
                                                                linha.sl,
                                                                linha.lt0,
                                                                linha.lt1,
                                                                linha.qv,
                                                                listOf(veiculo)
                                                            )
                                                        )
                                                    )
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                            adapterChegada.notifyDataSetChanged()
                        }
                    }
                } else {
                    Log.d("teste", "Erro: ${response.body()}")
                    Log.d("teste", "Erro Body: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<Chegada>, t: Throwable) {
                Log.d("teste", "error: " + t)
            }
        })
    }
}



