package br.vino.transmobisp.ui.main_activity.fragments.lines

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.vino.transmobisp.BuildConfig
import br.vino.transmobisp.model.VehicleLine
import br.vino.transmobisp.model.VehicleResponse
import br.vino.transmobisp.service.olho_vivo.RetrofitClient
import kotlinx.coroutines.launch
import retrofit2.Response

class LinesViewModel : ViewModel() {

    private val apiKey = BuildConfig.OLHO_VIVO_API_KEY
    private val _vehiclesLine = MutableLiveData<MutableList<VehicleLine>>()
    val vehiclesLine: LiveData<MutableList<VehicleLine>> = _vehiclesLine

    fun fetchVeiculosLine() {
        viewModelScope.launch {
            try {
                val service = RetrofitClient.instance
                val authResponse: Response<Boolean> = service.authenticate(apiKey)
                if (authResponse.isSuccessful && authResponse.body() == true) {
                    val vehicleResponse: Response<VehicleResponse> = service.getVehicles()
                    if (vehicleResponse.isSuccessful) {
                        val vehiclesResponse = vehicleResponse.body()
                        val vehiclesLineFromResponse = vehiclesResponse?.l ?: emptyList()
                        if (vehiclesLineFromResponse.isNotEmpty()) {
                            _vehiclesLine.value = vehiclesLineFromResponse.toMutableList()
                        }
                    } else {
                        Log.e("API", "${vehicleResponse.errorBody()?.string()}")
                    }
                } else {
                    Log.e("API", "${authResponse.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("API", "Error fetching vehicles", e)
                // Tratar erros
            }
        }
    }

}