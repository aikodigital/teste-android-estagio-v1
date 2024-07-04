package br.vino.transmobisp.ui.line_list_from_stop_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import br.vino.transmobisp.R
import br.vino.transmobisp.databinding.ActivityLineListFromStopBinding
import br.vino.transmobisp.databinding.ActivityStopsFromLineBinding
import br.vino.transmobisp.model.Stop
import br.vino.transmobisp.model.VehicleLine
import br.vino.transmobisp.model.vehicles_lines_from_stop.LineWithVehicles
import br.vino.transmobisp.ui.stop_vehicles_forecast_activity.StopVehiclesForecastActivity
import br.vino.transmobisp.ui.stops_from_line_activity.StopsFromLineAdapter
import br.vino.transmobisp.ui.stops_from_line_activity.StopsFromLineViewModel
import br.vino.transmobisp.ui.vehicles_list_forecast_activity.VehiclesListForecastActivity

class LineListFromStopActivity : AppCompatActivity(), LineListFromStopAdapter.OnItemClickListener {

    private val binding by lazy { ActivityLineListFromStopBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<LineListFromStopViewModel>()
    private lateinit var adapter: LineListFromStopAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val stop = intent.getSerializableExtra("stop") as? Stop

        viewModel.getLineListFromStop(stop!!.cp.toString())

        binding.lineListFromStopRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = LineListFromStopAdapter(emptyList(), this)
        binding.lineListFromStopRecyclerView.adapter = adapter

        supportActionBar?.title = "Linhas da Parada ${stop.np}"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupListeners()
    }

    private fun setupListeners(){
        viewModel.lineListFromStop.observe(this){
            adapter.updateLines(it.p.l)
        }
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

    override fun onItemClick(lineWithVehicles: LineWithVehicles) {
        //VehiclesListForecastActivity
        val intent = Intent(this, VehiclesListForecastActivity::class.java)
        intent.putExtra("lineWithVehicles", lineWithVehicles)
        startActivity(intent)
    }
}