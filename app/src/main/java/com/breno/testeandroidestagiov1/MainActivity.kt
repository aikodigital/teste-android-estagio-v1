package com.breno.testeandroidestagiov1


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.breno.testeandroidestagiov1.databinding.ActivityMainBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.btnSearch.setOnClickListener {
            val searchTerm = binding.searchField.text.toString()
            searchAndFilter(searchTerm)
        }
        binding.btnFilter.setOnClickListener {
            filterResults()
        }

        lifecycleScope.launch {
            val sucesso = autenticar()
            if (sucesso) {
                Toast.makeText(this@MainActivity, "Autenticado com sucesso", Toast.LENGTH_SHORT).show()

                val responsePosicoes = obterPosicoes()
                responsePosicoes?.let {
                    val posicoes = JSONObject(it).getJSONArray("l")
                    runOnUiThread {
                        atualizarMapa(posicoes)
                    }
                } ?: run {
                    Log.e("MainActivity", "Falha ao obter posições")
                }
            } else {
                Toast.makeText(this@MainActivity, "Falha na autenticação", Toast.LENGTH_SHORT).show()
                Log.e("MainActivity", "Falha na autenticação")
            }
        }
    }

    private fun atualizarMapa(posicoes: JSONArray) {
        mMap.clear()
        for (i in 0 until posicoes.length()) {
            val linha = posicoes.getJSONObject(i)
            val veiculos = linha.getJSONArray("vs")
            for (j in 0 until veiculos.length()) {
                val veiculo = veiculos.getJSONObject(j)
                val lat = veiculo.getDouble("py")
                val lng = veiculo.getDouble("px")
                val posicao = LatLng(lat, lng)
                mMap.addMarker(MarkerOptions().position(posicao).title("Veículo ${veiculo.getString("p")}"))
                Log.d("MainActivity", "Adicionando marcador em: ($lat, $lng)")
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val saoPaulo = LatLng(-23.550520, -46.633308)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(saoPaulo, 12f))
    }

    private fun searchAndFilter(searchTerm: String) {
        lifecycleScope.launch {
            val response = obterInformacoesLinhas(searchTerm)
            response?.let {
                Log.d("MainActivity", "Informações das linhas: $it")
                // Iniciar ResultActivity com o resultado da pesquisa
                val intent = Intent(this@MainActivity, ResultActivity::class.java)
                intent.putExtra("result", it)
                startActivity(intent)
            } ?: run {
                Log.e("MainActivity", "Falha ao obter informações das linhas")
                Toast.makeText(this@MainActivity, "Falha ao obter informações das linhas", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun filterResults() {
        lifecycleScope.launch {
            val response = obterInformacoesParadas()
            response?.let {
                Log.d("MainActivity", "Informações das paradas: $it")
                Toast.makeText(this@MainActivity, "Resultado do filtro: $it", Toast.LENGTH_LONG).show()
            } ?: run {
                Log.e("MainActivity", "Falha ao obter informações das paradas")
                Toast.makeText(this@MainActivity, "Falha ao obter informações das paradas", Toast.LENGTH_LONG).show()
            }
        }
    }

    private suspend fun obterInformacoesLinhas(searchTerm: String): String? {
        return withContext(Dispatchers.IO) {
            try {
                val url = URL("http://api.olhovivo.sptrans.com.br/v2.1/Linha/Buscar?termosBusca=$searchTerm")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.doInput = true

                sessionCookie?.let {
                    connection.setRequestProperty("Cookie", it)
                }

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    connection.inputStream.bufferedReader().readText()
                } else {
                    Log.e("ObterInformacoesLinhas", "Erro ao obter informações das linhas. Código de resposta: $responseCode")
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("ObterInformacoesLinhas", "Erro ao obter informações das linhas: ${e.message}")
                null
            }
        }
    }

    private suspend fun obterInformacoesParadas(): String? {
        return withContext(Dispatchers.IO) {
            try {
                val url = URL("http://api.olhovivo.sptrans.com.br/v2.1/Parada/Buscar")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.doInput = true

                sessionCookie?.let {
                    connection.setRequestProperty("Cookie", it)
                }

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    connection.inputStream.bufferedReader().readText()
                } else {
                    Log.e("ObterInformacoesParadas", "Erro ao obter informações das paradas. Código de resposta: $responseCode")
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("ObterInformacoesParadas", "Erro ao obter informações das paradas: ${e.message}")
                null
            }
        }
    }

    private suspend fun obterPrevisaoChegada(paradaId: Int): String? {
        return withContext(Dispatchers.IO) {
            try {
                val url = URL("http://api.olhovivo.sptrans.com.br/v2.1/Previsao/Parada?codigoParada=$paradaId")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.doInput = true

                sessionCookie?.let {
                    connection.setRequestProperty("Cookie", it)
                }

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    connection.inputStream.bufferedReader().readText()
                } else {
                    Log.e("ObterPrevisaoChegada", "Erro ao obter previsão de chegada. Código de resposta: $responseCode")
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("ObterPrevisaoChegada", "Erro ao obter previsão de chegada: ${e.message}")
                null
            }
        }
    }
}