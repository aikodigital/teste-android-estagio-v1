package br.vino.transmobisp.ui.stops_from_line_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import br.vino.transmobisp.databinding.ActivityStopsFromLineBinding
import br.vino.transmobisp.model.VehicleLine
import br.vino.transmobisp.model.stops_from_line.StopWithVehicles
import br.vino.transmobisp.ui.main_activity.fragments.lines.LineListAdapter
import br.vino.transmobisp.ui.stop_vehicles_forecast_activity.StopVehiclesForecastActivity

class StopsFromLineActivity : AppCompatActivity(), StopsFromLineAdapter.OnItemClickListener {

    private val binding by lazy { ActivityStopsFromLineBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<StopsFromLineViewModel>()
    private lateinit var adapter: StopsFromLineAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val vehicleLine = intent.getSerializableExtra("vehicleLine") as? VehicleLine

        viewModel.getStopsFromLine(vehicleLine!!.cl.toString())

        binding.stopsFromLineRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = StopsFromLineAdapter(emptyList(), this)
        binding.stopsFromLineRecyclerView.adapter = adapter

        supportActionBar?.title = "Paradas da linha ${vehicleLine.c}"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupListeners()

    }

    private fun setupListeners(){
        viewModel.stopsFromLine.observe(this){stopsFromLine ->
            adapter.updateStopsFromline(stopsFromLine.ps)
        }

        binding.stopsFromLineSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(text: String?): Boolean {
                adapter.filter(text.orEmpty())
                return true
            }

        })
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

    override fun onItemClick(stopWithVehicles: StopWithVehicles) {
        val intent = Intent(this, StopVehiclesForecastActivity::class.java)
        intent.putExtra("stopWithVehicles", stopWithVehicles)
        startActivity(intent)
    }
}