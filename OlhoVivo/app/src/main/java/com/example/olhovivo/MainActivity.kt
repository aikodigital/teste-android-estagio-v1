package com.example.olhovivo

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.olhovivo.model.Linha
import com.example.olhovivo.model.Parada
import com.example.olhovivo.network.ApiClient
import com.example.olhovivo.model.PosicoesResponse
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

//        searchBar = findViewById(R.id.search_bar)
//        filterSpinner = findViewById(R.id.filter_spinner)
//
//        // Configurar Spinner com opções de filtro
//        val filterOptions = arrayOf("Todas", "Linhas específicas", "Paradas próximas")
//        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, filterOptions)
//        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        filterSpinner.adapter = spinnerAdapter
//
//        // Configurar a barra de pesquisa
//        searchBar.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable?) {
//                filterData(s.toString(), filterSpinner.selectedItem.toString())
//            }
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
//        })
//
//        filterSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                filterData(searchBar.text.toString(), filterOptions[position])
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {}
//        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        authenticateAndFetchData()
    }

    private fun authenticateAndFetchData() {
        val token = BuildConfig.API_KEY
        ApiClient.api.autenticar(token).enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) =
                if (response.isSuccessful && response.body() == true) {
                    fetchPosicoes()
                } else {
                    Log.e("AuthError", "Error")
                    Toast.makeText(this@MapsActivity, "Error", Toast.LENGTH_SHORT).show()
                }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.e("AuthError", "Error: ${t.message}")
                Toast.makeText(this@MapsActivity, "Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Setup the initial state of the map
        val saoPaulo = LatLng(-23.55052, -46.633309)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(saoPaulo, 10f))
    }

    private fun updateMap(posicoes: PosicoesResponse) {
        mMap.clear()
        posicoes.l.forEach { linha ->
            linha.vs.forEach { veiculo ->
                val position = LatLng(veiculo.py, veiculo.px)
                mMap.addMarker(MarkerOptions().position(position).title(linha.c))
            }
        }
    }

    private fun displayMarkers(paradas: List<Parada>) {
        mMap.clear()
        paradas.forEach { parada ->
            val position = LatLng(parada.py, parada.px)
            mMap.addMarker(MarkerOptions().position(position).title(parada.np))
        }
    }
}
