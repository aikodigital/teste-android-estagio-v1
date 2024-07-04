package br.vino.transmobisp.ui.stop_vehicles_forecast_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import br.vino.transmobisp.databinding.ActivityStopVehiclesForecastBinding
import br.vino.transmobisp.model.VehicleLine
import br.vino.transmobisp.model.stops_from_line.StopWithVehicles

class StopVehiclesForecastActivity : AppCompatActivity() {

    private val binding by lazy { ActivityStopVehiclesForecastBinding.inflate(layoutInflater) }
    private lateinit var adapter: StopVehiclesForecastAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val stopWithVehicles = intent.getSerializableExtra("stopWithVehicles") as? StopWithVehicles

        supportActionBar?.title = "Veiculos na parada ${stopWithVehicles!!.np}"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.stopVehicleForecastRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = StopVehiclesForecastAdapter(stopWithVehicles!!.vs)
        binding.stopVehicleForecastRecyclerView.adapter = adapter

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish() // Voltar quando o botão de voltar (seta) for pressionado
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}