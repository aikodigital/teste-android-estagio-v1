package com.example.aiko_desafio

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.aiko_desafio.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@SuppressLint("StaticFieldLeak")
//Binding para obter id's do XML.
private lateinit var b: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Função para enviar requisição para autenticar.
        autentica()

        // Configurando o binding
        b = ActivityMainBinding.inflate(layoutInflater)
        val view = b.root;
        setContentView(view)

        // Botão para ir para ir para a activity do mapa dos veiculos.
        b.bntVeiculos.setOnClickListener {
            val intent = Intent(this, Mapa_veiculos::class.java)
            startActivity(intent)
        }
        // Botão para ir para ir para a activity do mapa das paradas.
        b.bntParadas.setOnClickListener {
            val intent = Intent(this, Mapa_paradas::class.java)
            startActivity(intent)
        }
        // Botão para ir para ir para a activity das linhas.
        b.bntLinhas.setOnClickListener {
            val intent = Intent(this, Tela_Linhas::class.java)
            startActivity(intent)
        }
        // Botão para ir para ir para a activity das previsões de uma certa parada.
        b.bntPrevisao.setOnClickListener {
            val intent = Intent(this, PrevisaoChegada::class.java)
            startActivity(intent)
        }
    }

    // Função para autenticar.
    private fun autentica(){
        // Instanciando o objeto do retrofit.
        val retrofit = retrofit().retrofit
        retrofit.autentica().enqueue(object : Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.isSuccessful){
                    if (response.body() == true){
                        Log.d("Resposta", "Body: ${response.body()}")
                    }else{
                        Log.d("Resposta", "false")
                    }
                }else{
                    Log.d("Resposta", "Erro de resposta: ${response.errorBody()?.string()}")
                }
            }
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.d("Resposta", "Error:  "+t)
            }
        })
    }
}

