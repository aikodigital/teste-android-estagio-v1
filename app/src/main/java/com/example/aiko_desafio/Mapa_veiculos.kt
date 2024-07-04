package com.example.aiko_desafio

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.aiko_desafio.databinding.ActivityMapaVeiculosBinding
import com.example.aiko_desafio.objetos.PosicaoVeiculos
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST

@SuppressLint("StaticFieldLeak")
//Novamente, iniciando o binding.
private lateinit var b: ActivityMapaVeiculosBinding

class Mapa_veiculos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configurando o binding
        b = ActivityMapaVeiculosBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)

        // Configuração da API do GoogleMaps.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_onibus) as SupportMapFragment
        mapFragment.getMapAsync { googleMap->
            val camera = CameraPosition.Builder()
                .target(LatLng(-23.5489,-46.6388))
                .zoom(10f)
                .build()
            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(camera))
            googleMap.clear()
            // Função para obter posições dos veiculos.
            posicao(googleMap)
        }

        b.bntVoltar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)

        }
    }
    // Função que faz a requisição dos pontos no mapa.
    private fun posicao(googleMap: GoogleMap){
        // Instanciando o retrofit.
        val retrofit = retrofit().retrofit
        retrofit.getPosicao().enqueue(object : Callback<PosicaoVeiculos>{
            override fun onResponse(call: Call<PosicaoVeiculos>, response: Response<PosicaoVeiculos>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        // Laços para pegar uma certa variavel.
                        for (Linha_veiculo in it.l) {
                            for (Veiculo_veiculo in Linha_veiculo.vs) {
                                // Aqui cria os marcadores dos veiculos no Google Maps
                                googleMap.addMarker(MarkerOptions().
                                title(Veiculo_veiculo.p.toString()).
                                position(LatLng(Veiculo_veiculo.py,Veiculo_veiculo.px)))
                            }
                        }
               }
                }else{
                    Log.d("teste", "Erro: ${response.body()}")
                    Log.d("teste", "Erro Body: ${response.errorBody()?.string()}")
                }
            }
            override fun onFailure(call: Call<PosicaoVeiculos>, t: Throwable) {
                Log.d("teste","error: "+t)
            }
        })
    }
}