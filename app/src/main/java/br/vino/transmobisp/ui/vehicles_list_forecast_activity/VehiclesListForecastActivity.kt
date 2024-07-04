package br.vino.transmobisp.ui.vehicles_list_forecast_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import br.vino.transmobisp.R
import br.vino.transmobisp.databinding.ActivityLineListFromStopBinding
import br.vino.transmobisp.databinding.ActivityVehiclesListForecastBinding
import br.vino.transmobisp.model.Stop
import br.vino.transmobisp.model.vehicles_lines_from_stop.LineWithVehicles
import br.vino.transmobisp.ui.line_list_from_stop_activity.LineListFromStopAdapter
import br.vino.transmobisp.ui.line_list_from_stop_activity.LineListFromStopViewModel

class VehiclesListForecastActivity : AppCompatActivity() {

    private val binding by lazy { ActivityVehiclesListForecastBinding.inflate(layoutInflater) }
    private lateinit var adapter: VehiclesListForecastAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val lineWithVehicles = intent.getSerializableExtra("lineWithVehicles") as? LineWithVehicles

        supportActionBar?.title = "Veiculos da Linha ${lineWithVehicles!!.c}"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.vehiclesListForecastRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = VehiclesListForecastAdapter(lineWithVehicles!!.vs)
        binding.vehiclesListForecastRecyclerView.adapter = adapter

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