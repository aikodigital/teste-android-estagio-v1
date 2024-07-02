package br.com.samuel.testeaiko.ui.presentation.stops


import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.samuel.testeaiko.R
import br.com.samuel.testeaiko.core.domain.model.BusPosition
import br.com.samuel.testeaiko.core.domain.model.BusStop
import br.com.samuel.testeaiko.databinding.ActivityStopsBinding
import dagger.hilt.android.AndroidEntryPoint
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import java.time.ZonedDateTime

/**
 * Optei por usar view no lugar de compose por bugs na renderização do mapa durante o desenvolvimento
 */
@AndroidEntryPoint
class StopsActivity : AppCompatActivity() {

    private lateinit var vb: ActivityStopsBinding
    private val vm: StopsVM by viewModels()

    private lateinit var stopsAdapter: StopsAdapter
    private lateinit var busesAdapter: BusAdapter

    private var mapController: IMapController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vb = ActivityStopsBinding.inflate(layoutInflater)
        setContentView(vb.root)

        // Inicializa as configurações do open street map
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))

        if (intent?.getIntExtra(LINE_ID, -1) != -1) {
            vm.lineId = intent?.getIntExtra(LINE_ID, -1)
            vb.toolbar.subtitle =
                getString(R.string.line_subtitle, intent.getStringExtra(LINE_NAME))
        }

        if (intent?.getIntExtra(CORRIDOR_ID, -1) != -1) {
            vm.corridorId = intent?.getIntExtra(CORRIDOR_ID, -1)
            vb.toolbar.subtitle = getString(
                R.string.corridor_subtitle, intent.getStringExtra(
                    CORRIDOR_NAME
                )
            )
        }

        setupViews()
        observeEvents()

        vm.initialize()
        vm.search()
    }

    private fun setupViews() {
        vb.toolbar.title = getString(R.string.stops)
        setSupportActionBar(vb.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        vb.map.setTileSource(TileSourceFactory.MAPNIK)

        mapController = vb.map.controller
        mapController?.setCenter(GeoPoint(-23.56025629147249, -46.632360082442176))
        mapController?.setZoom(16.5)
        vb.map.invalidate()

        // Paradas
        stopsAdapter = StopsAdapter(stopsAdapterCallback)
        vb.rvStops.adapter = stopsAdapter
        vb.rvStops.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        vb.rvStops.isNestedScrollingEnabled = false
        vb.rvStops.setHasFixedSize(true)

        // Ônibus
        busesAdapter = BusAdapter(busesAdapterCallback)
        vb.rvBuses.adapter = busesAdapter
        vb.rvBuses.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        vb.rvBuses.isNestedScrollingEnabled = false
        vb.rvBuses.setHasFixedSize(true)
    }

    private fun observeEvents() {
        // Atualiza as paradas
        vm.stops.observe(this) { stops ->
            if (stops.isNotEmpty()) {
                vb.svContent.visibility = View.VISIBLE
                vb.tvStops.visibility = View.VISIBLE
                vb.rvStops.visibility = View.VISIBLE
                vb.tvNoStopsFound.visibility = View.GONE

                stopsAdapter.addItems(stops)
                updateMapStops(stops)
            }
        }

        // Atualiza as posições dos ônibus
        vm.busPositions.observe(this) { positions ->
            if (positions.isNotEmpty()) {
                vb.svContent.visibility = View.VISIBLE
                vb.tvBuses.visibility = View.VISIBLE
                vb.rvBuses.visibility = View.VISIBLE
                vb.tvNoStopsFound.visibility = View.GONE

                busesAdapter.addItems(positions)
                updateMapBuses(positions)
            }
        }

        // Atualiza as paradas com as previsões dos ônibus
        vm.forecastResult.observe(this) { result ->
            val busStop = vm.stops.value?.find { it.code == result.stopCode }
            busStop?.let { stop ->
                val position = vm.stops.value?.indexOf(stop) ?: -1
                if (position != -1) {
                    stopsAdapter.addForecasts(result.forecasts, position)
                }
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        vb.map.onResume()
    }

    override fun onPause() {
        super.onPause()
        vb.map.onPause()
    }

    // Adiciona as paradas no mapa
    private fun updateMapStops(stops: List<BusStop>) {
        stops.forEachIndexed { index, busStop ->
            val marker = Marker(vb.map).apply {
                title = busStop.name
                position = GeoPoint(busStop.latitude, busStop.longitude)
                icon = ContextCompat.getDrawable(this@StopsActivity, R.drawable.ic_marker)
            }
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)

            // Centraliza o mapa na primeira parada encontrada na lista
            if (index == 0) {
                mapController?.setCenter(GeoPoint(busStop.latitude, busStop.longitude))
            }

            vb.map.overlays.add(marker)
        }

        vb.map.invalidate()
    }

    // Adiciona as posições dos ônibus no mapa
    private fun updateMapBuses(positions: List<BusPosition>) {
        positions.forEach { busPosition ->
            val marker = Marker(vb.map).apply {
                position = GeoPoint(busPosition.latitude, busPosition.longitude)
                icon = ContextCompat.getDrawable(this@StopsActivity, R.drawable.ic_bus)
            }
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)

            vb.map.overlays.add(marker)
        }

        vb.map.invalidate()
    }

    private val stopsAdapterCallback = object : StopsAdapter.Callback {
        override fun onClick(position: Int) {
            val stopPosition = stopsAdapter.getItem(position)
            mapController?.let { controller ->
                controller.setCenter(GeoPoint(stopPosition.latitude, stopPosition.longitude))
                vb.map.invalidate()
            }
        }
    }

    private val busesAdapterCallback = object : BusAdapter.Callback {
        override fun onClick(position: Int) {
            val busPosition = busesAdapter.getItem(position)
            mapController?.let { controller ->
                controller.setCenter(GeoPoint(busPosition.latitude, busPosition.longitude))
                vb.map.invalidate()
            }
        }
    }

    companion object {
        const val LINE_ID = "line_id"
        const val LINE_NAME = "line_name"
        const val CORRIDOR_ID = "corridor_id"
        const val CORRIDOR_NAME = "corridor_name"
    }

}