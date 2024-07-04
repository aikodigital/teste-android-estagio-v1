package com.example.aiko_desafio

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aiko_desafio.databinding.ActivityMapaParadasBinding
import com.example.aiko_desafio.objetos.Paradas
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import kotlin.math.log

@SuppressLint("StaticFieldLeak")
//Novamente, iniciando o binding.
private lateinit var b: ActivityMapaParadasBinding

class Mapa_paradas : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMapaParadasBinding.inflate(layoutInflater)
        val view = b.root
        setContentView(view)

        // Configuração da API do GoogleMaps.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_parada) as SupportMapFragment
        mapFragment.getMapAsync { googleMap ->
            val camera = CameraPosition.Builder()
                .target(LatLng(-23.5489, -46.6388))
                .zoom(10f)
                .build()
            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(camera))

            b.bntParadas.setOnClickListener {
                googleMap.clear()
                var text = b.editParadas.text.toString()
                // Retorna a posição da parada informada.
                paradas(googleMap,text)
            }
        }

        b.bntVoltar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
        }
    }
    // Função que faz a requisição dos pontos no mapa.
    private fun paradas(googleMap: GoogleMap, termos : String) {
        // Instanciando o retrofit.
        val retrofit = retrofit().retrofit
        Log.d("teste",termos)
        retrofit.getParadas(termos).enqueue(object : Callback<List<Paradas>> {
            override fun onResponse(call: Call<List<Paradas>>, response: Response<List<Paradas>>) {
                if (response.isSuccessful) {
                    if (response.body()!!.isEmpty()){
                        Log.d("teste",response.body().toString())
                        Toast.makeText(this@Mapa_paradas,"Parada invalida!",Toast.LENGTH_LONG).show()
                    }else{
                        response.body()?.let {
                            val paradas = response.body()
                            // Aqui cria os marcadores das paradas no Google Maps
                            paradas!!.map {
                                googleMap.addMarker(MarkerOptions().
                                title(it.np).
                                position(LatLng(it.py,it.px)))
                                Log.d("teste", "id: "+it.cp)
                            }
                        }
                    }
                } else {
                    Log.d("teste", "Erro: ${response.body()}")
                    Log.d("teste", "Erro Body: ${response.errorBody()?.string()}")
                }
            }
            override fun onFailure(call: Call<List<Paradas>>, t: Throwable) {
                Log.d("teste", "error: " + t)
            }
        })
    }

}