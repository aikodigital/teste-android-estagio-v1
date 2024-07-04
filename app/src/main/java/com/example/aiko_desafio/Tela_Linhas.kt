package com.example.aiko_desafio

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aiko_desafio.Adapter.AdpterLinhas
import com.example.aiko_desafio.databinding.ActivityTelaLinhasBinding
import com.example.aiko_desafio.objetos.Linhas
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@SuppressLint("StaticFieldLeak")
//Novamente, iniciando o binding.
private lateinit var b: ActivityTelaLinhasBinding
@SuppressLint("StaticFieldLeak")
//Iniciando as funções do RecycleView para imprimir os dados na tela.
private lateinit var adapterLinhas: AdpterLinhas
private val listaLinhas: MutableList<Linhas> = mutableListOf()

class Tela_Linhas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configurando o binding.
        b = ActivityTelaLinhasBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)

        // Configurando o RecycleView.
        val recyclerViewLinhas = b.listaLinha
        recyclerViewLinhas.layoutManager = LinearLayoutManager(this)
        recyclerViewLinhas.setHasFixedSize(true)
        adapterLinhas = AdpterLinhas(this, listaLinhas)
        recyclerViewLinhas.adapter = adapterLinhas

        // Botão para voltar para o menu principal.
        b.bntVoltar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
        }

        // Botão para enviar a requisição
        b.bntLinhas.setOnClickListener {
            var texto = b.editLinha.text.toString()

            // Verificação sobre qual escolha o usuário fez
            if (b.bntAmbos.isChecked){
                linhas(texto, "0")
            }
            else if (b.bnt1.isChecked){
                linhas(texto, "1")
            }
            else if (b.bnt2.isChecked){
                linhas(texto, "2")
            }else{
                linhas(texto, "3")
            }
        }
    }

    // Função que faz a requisição das linhas
    private fun linhas(linha : String, sentido : String) {
        val retrofit = retrofit().retrofit

        // Verifica o valor informado.
        var call : Call<List<Linhas>>? = when(sentido){
            "0" -> retrofit.getLinhas(linha)
            "1" -> retrofit.getLinhasSentido(linha, sentido)
            "2" -> retrofit.getLinhasSentido(linha, sentido)
            else -> null
        }

        // Verifica se o valor informado é nulo.
        if(call == null){
            // Imprime um "toast" na tela informando que o sentido é invalido
            Toast.makeText(this@Tela_Linhas,"Sentido invalido!!", Toast.LENGTH_LONG).show()
        }else{
            call.enqueue(object : Callback<List<Linhas>> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(call: Call<List<Linhas>>, response: Response<List<Linhas>>) {
                    if (response.isSuccessful) {
                        if (response.body()!!.isEmpty()){

                            // Imprime um "toast" na tela informando que a linha é invalida
                            Toast.makeText(this@Tela_Linhas,"Linha invalida!", Toast.LENGTH_LONG).show()
                        }else{

                            // Codigo que envia as informações pra RecycleView
                            response.body()?.let { linhas ->
                                listaLinhas.clear()
                                listaLinhas.addAll(linhas)
                                adapterLinhas.notifyDataSetChanged()
                            }
                        }
                    } else {
                        Log.d("teste", "Erro: ${response.body()}")
                        Log.d("teste", "Erro Body: ${response.errorBody()?.string()}")
                    }
                }
                override fun onFailure(call: Call<List<Linhas>>, t: Throwable) {
                    Log.d("teste", "error: " + t)
                }
            })
        }
    }
}

