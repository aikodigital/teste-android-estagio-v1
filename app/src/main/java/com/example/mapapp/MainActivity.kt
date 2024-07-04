package com.example.mapapp

import java.util.Calendar
import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import org.osmdroid.config.Configuration.*
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus
import org.osmdroid.views.overlay.OverlayItem

import java.util.ArrayList

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private val PERMISSIONS_REQUEST_CODE = 101
    private val REQUIRED_PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.INTERNET,
        Manifest.permission.ACCESS_NETWORK_STATE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    private lateinit var map: MapView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))
        setContentView(R.layout.activity_main)

        if (!hasPermissions(REQUIRED_PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE)
        }

        map = findViewById<MapView>(R.id.map)
        map.setTileSource(TileSourceFactory.MAPNIK)

        val mapController = map.controller
        mapController.setZoom(18)
        val startPoint = GeoPoint(-23.5475, -46.63611);
        mapController.setCenter(startPoint);

        //Client
        lifecycleScope.launch(Dispatchers.IO) {
            val items = ArrayList<OverlayItem>()

            // Retrofit para fazer a autenticação
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(ApiService::class.java)

            // Fazer a autenticação
            try {
                val authResponse = service.authenticate().execute()
                if (authResponse.isSuccessful) {
                    val token = authResponse.headers().get("Set-Cookie")

                    val headers = authResponse.headers()
                    val cookies = headers.values("Set-Cookie")
                    var apiCredentials = ""
                    for (cookie in cookies) {
                        if (cookie.startsWith("apiCredentials=")) {
                            apiCredentials = cookie
                            break
                        }
                    }
                    // Botões da tela
                    val buttonVehiclePositions: Button = findViewById(R.id.button_vehicle_positions)
                    val buttonBack: Button = findViewById(R.id.button_back)
                    val buttonLines: Button = findViewById(R.id.button_lines)
                    val buttonStops: Button = findViewById(R.id.button_stops)
                    val buttonArrivalForecast: Button = findViewById(R.id.button_arrival_forecast)
                    val searchView: SearchView = findViewById(R.id.searchView)
                    val listView: ListView = findViewById(R.id.listView)
                    val textViewResult: TextView = findViewById(R.id.textViewResult)
                    val textClickOnIcon: TextView = findViewById(R.id.textViewAlert)

                    // Botão de voltar para a tela anterior
                    buttonBack.setOnClickListener {
                        //Limpa os items antes de adicionar novos
                        items.clear()
                        //Limpa o cache
                        map.overlays.clear()
                        runOnUiThread {
                            textClickOnIcon.visibility = View.GONE
                            textViewResult.visibility = View.GONE
                            buttonBack.visibility = View.GONE
                            searchView.visibility = View.GONE
                            listView.visibility = View.GONE
                            map.visibility = View.GONE
                            buttonVehiclePositions.visibility = View.VISIBLE
                            buttonLines.visibility = View.VISIBLE
                            buttonStops.visibility = View.VISIBLE
                            buttonArrivalForecast.visibility = View.VISIBLE
                        }
                    }

                    // Exibir no mapa onde os veículos estavam na sua última atualização.
                    buttonVehiclePositions.setOnClickListener {
                        lifecycleScope.launch(Dispatchers.IO) {
                            val locationResponse = service.getAllLocation(apiCredentials).execute()
                            val allPosition = locationResponse.body()
                            val currentTime = Calendar.getInstance()
                            // Filtro para pegar apenas os ônibus que estão na mesma hora e minuto que a hora atual
                            allPosition?.l?.forEach { busLine ->
                                busLine.vs.filter { bus ->
                                    val time = bus.ta.split("T")[1].split(":")
                                    val hour = time[0].toInt()
                                    val minute = time[1].toInt()
                                    hour == currentTime.get(Calendar.HOUR_OF_DAY) && minute == currentTime.get(
                                        Calendar.MINUTE
                                    )
                                }.forEach { bus ->
                                    // println("Hora: ${bus.ta}, PX: ${bus.px}, PY: ${bus.py}")
                                    items.add(
                                        OverlayItem(
                                            "Ônibus ${bus.p}",
                                            "Posição",
                                            GeoPoint(bus.py, bus.px)
                                        ).apply {
                                            setMarker(resources.getDrawable(R.drawable.bus_marker))
                                        })
                                }
                            }
                            // Adicionar ícone de ônibus no mapa
                            var overlay =
                                ItemizedOverlayWithFocus<OverlayItem>(this@MainActivity, items,
                                    object :
                                        ItemizedIconOverlay.OnItemGestureListener<OverlayItem> {
                                        override fun onItemSingleTapUp(
                                            index: Int,
                                            item: OverlayItem
                                        ): Boolean {
                                            return true
                                        }

                                        override fun onItemLongPress(
                                            index: Int,
                                            item: OverlayItem
                                        ): Boolean {
                                            return false
                                        }
                                    })
                            // Adicionar overlay no mapa
                            overlay.setFocusItemsOnTap(true);
                            map.overlays.add(overlay);
                            // Mudar a visibilidade dos botões
                            runOnUiThread {
                                buttonBack.visibility = View.VISIBLE
                                map.visibility = View.VISIBLE
                                buttonVehiclePositions.visibility = View.GONE
                                buttonLines.visibility = View.GONE
                                buttonStops.visibility = View.GONE
                                buttonArrivalForecast.visibility = View.GONE
                                searchView.visibility = View.GONE
                            }
                        }
                    }

                    // Linhas
                    buttonLines.setOnClickListener {
                        searchView.visibility = View.VISIBLE
                        buttonBack.visibility = View.VISIBLE
                        buttonVehiclePositions.visibility = View.GONE
                        buttonLines.visibility = View.GONE
                        buttonStops.visibility = View.GONE
                        buttonArrivalForecast.visibility = View.GONE
                        searchView.queryHint = "Digite o código ou nome da linha"
                        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                            override fun onQueryTextSubmit(query: String?): Boolean {
                                if (!query.isNullOrEmpty()) {
                                    lifecycleScope.launch(Dispatchers.IO) {
                                        val lineResponse =
                                            service.getLine(apiCredentials, query).execute()
                                        val lines = lineResponse.body() ?: listOf()
                                        var textResult = ""
                                        lines.forEach { line ->
                                            textResult += "Código identificador da linha: \n" +
                                                    "${line.cl}\n" +
                                                    "Informa a primeira parte do letreiro numérico da linha: \n" +
                                                    "${line.lt}\n" +
                                                    "Informa o letreiro descritivo da linha no sentido Terminal Principal: \n" +
                                                    "${line.tp}\n" +
                                                    "Informa o letreiro descritivo da linha no sentido Terminal Secundário: \n" +
                                                    "${line.ts}\n\n"
                                        }
                                        runOnUiThread {
                                            textViewResult.text = textResult
                                            textViewResult.visibility = View.VISIBLE
                                            listView.visibility = View.VISIBLE
                                            buttonBack.visibility = View.VISIBLE
                                            map.visibility = View.GONE
                                            searchView.visibility = View.GONE
                                        }
                                    }
                                    searchView.setQuery("", false)
                                    searchView.clearFocus()
                                }
                                return true
                            }

                            override fun onQueryTextChange(newText: String?): Boolean {
                                // Implementação opcional. Pode ser usado para busca em tempo real enquanto o usuário digita.
                                return false
                            }
                        })
                    }

                    // Buscar parada de ônibus
                    buttonStops.setOnClickListener {
                        runOnUiThread{
                        buttonVehiclePositions.visibility = View.GONE
                        buttonLines.visibility = View.GONE
                        buttonStops.visibility = View.GONE
                        buttonArrivalForecast.visibility = View.GONE
                        searchView.visibility = View.VISIBLE
                        buttonBack.visibility = View.VISIBLE
                        }
                        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                            override fun onQueryTextSubmit(query: String?): Boolean {
                                if (!query.isNullOrEmpty()) {
                                    lifecycleScope.launch(Dispatchers.IO) {
                                        val stopResponse =
                                            service.getStop(apiCredentials, query).execute()
                                        val stops = stopResponse.body()
                                        runOnUiThread{
                                            mapController.setCenter(
                                                GeoPoint(
                                                    stops?.get(0)?.py!!,
                                                    stops.get(0).px
                                                )
                                            )
                                        }

                                        println(stops)
                                        stops?.forEach() { stop ->
                                            items.add(
                                                OverlayItem(
                                                    "${stop.np}\n",
                                                    "${stop.cp}",
                                                    GeoPoint(stop.py, stop.px)
                                                ).apply {
                                                    setMarker(resources.getDrawable(R.drawable.bus_stop))
                                                })
                                        }

                                        var overlay =
                                            ItemizedOverlayWithFocus<OverlayItem>(this@MainActivity,
                                                items,
                                                object :
                                                    ItemizedIconOverlay.OnItemGestureListener<OverlayItem> {
                                                    override fun onItemSingleTapUp(
                                                        index: Int,
                                                        item: OverlayItem
                                                    ): Boolean {
                                                        val stopCode = items[index].snippet
                                                        val stopName = items[index].title
                                                        AlertDialog.Builder(this@MainActivity)
                                                            .apply {
                                                                setTitle("Confirmação")
                                                                setMessage("Deseja ver próximo ônibus que chegará na $stopName?")
                                                                setPositiveButton("Sim") { dialog, which ->
                                                                    // Simula o clique no botão se o usuário confirmar
                                                                    buttonArrivalForecast.performClick()
                                                                    searchView.setQuery(
                                                                        stopCode,
                                                                        true
                                                                    ) // Define o código da parada na barra de pesquisa e submete a consulta
                                                                    searchView.setQuery("", false)
                                                                    //                                                    searchView.visibility = View.GONE
                                                                }
                                                                setNegativeButton("Não", null)
                                                                show()
                                                            }
                                                        return true
                                                    }

                                                    override fun onItemLongPress(
                                                        index: Int,
                                                        item: OverlayItem
                                                    ): Boolean {
                                                        return false
                                                    }
                                                })
                                        // Adicionar overlay no mapa
                                        overlay.setFocusItemsOnTap(true);
                                        map.overlays.add(overlay);

                                        runOnUiThread {
                                            textClickOnIcon.visibility = View.VISIBLE
                                            listView.visibility = View.GONE
                                            map.visibility = View.VISIBLE
                                            buttonBack.visibility = View.VISIBLE
                                            textViewResult.visibility = View.GONE
                                            buttonVehiclePositions.visibility = View.GONE
                                            buttonLines.visibility = View.GONE
                                            buttonStops.visibility = View.GONE
                                            buttonArrivalForecast.visibility = View.GONE
                                            searchView.visibility = View.GONE
                                        }
                                    }
                                }else{
                                    runOnUiThread {
                                        AlertDialog.Builder(this@MainActivity)
                                            .apply {
                                                setTitle("Erro")
                                                setMessage("Digite um código de parada válido")
                                                setPositiveButton("Ok", null)
                                                show()
                                            }
                                    }
                                }
                                searchView.clearFocus()
                                searchView.setQuery("", false)
                                return true
                            }

                            override fun onQueryTextChange(newText: String?): Boolean {
                                // Implementação opcional. Pode ser usado para busca em tempo real enquanto o usuário digita.
                                return false
                            }
                        })
                    }

                    // Previsão de chegada
                    buttonArrivalForecast.setOnClickListener {
                        textClickOnIcon.visibility = View.GONE
                        searchView.visibility = View.VISIBLE
                        buttonBack.visibility = View.VISIBLE
                        buttonVehiclePositions.visibility = View.GONE
                        buttonLines.visibility = View.GONE
                        buttonStops.visibility = View.GONE
                        buttonArrivalForecast.visibility = View.GONE
                        searchView.queryHint = "Digite o código da parada"
                        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                            override fun onQueryTextSubmit(query: String?): Boolean {
                                if (!query.isNullOrEmpty()) {
                                    lifecycleScope.launch(Dispatchers.IO) {
                                        val forecastResponse =
                                            service.getForecastStop(apiCredentials, query).execute()
                                        val forecastStop = forecastResponse.body()
                                        forecastStop?.let {
                                            var textResult =
                                                "Ponto de parada: ${forecastStop.p.np} \n" +
                                                        "Local: ${forecastStop.p.py}, ${forecastStop.p.px})"
                                            forecastStop.p.l.forEach { line ->
                                                textResult += "\nLinha: ${line.c} Sentido: ${line.sl}, Destino: ${line.lt0}, Origem: ${line.lt1}"
                                                line.vs.forEach { vehicle ->
                                                    textResult += "\nÔnibus: ${vehicle.p}\n" +
                                                            "Horário previsto: ${vehicle.t}\n\n"
                                                }
                                            }
                                            runOnUiThread {
                                                textViewResult.text = textResult
                                                textViewResult.visibility = View.VISIBLE
                                                listView.visibility = View.VISIBLE
                                                buttonBack.visibility = View.VISIBLE
                                                map.visibility = View.GONE
                                                searchView.visibility = View.GONE
                                            }
                                        }
                                    }
                                }
                                searchView.clearFocus()
                                searchView.setQuery("", false)
                                return true
                            }

                            override fun onQueryTextChange(newText: String?): Boolean {
                                // Implementação opcional. Pode ser usado para busca em tempo real enquanto o usuário digita.
                                return false
                            }
                        }
                        )
                    }

                } else {
                    println("Error authenticating: ${authResponse.errorBody()}")
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }

    private fun hasPermissions(permissions: Array<String>): Boolean = permissions.all {
        ActivityCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onResume() {
        super.onResume()
        map.onResume()
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                // Todas as permissões foram concedidas
            } else {
                // Algumas permissões foram negadas
            }
        }
    }
}