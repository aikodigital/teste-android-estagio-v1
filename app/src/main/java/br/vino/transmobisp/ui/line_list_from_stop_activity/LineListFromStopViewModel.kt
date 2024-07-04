package br.vino.transmobisp.ui.line_list_from_stop_activity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.vino.transmobisp.BuildConfig
import br.vino.transmobisp.model.vehicles_lines_from_stop.StopWithLinesAndVehicles
import br.vino.transmobisp.service.olho_vivo.RetrofitClient
import kotlinx.coroutines.launch
import retrofit2.Response

class LineListFromStopViewModel : ViewModel() {

    private val apiKey = BuildConfig.OLHO_VIVO_API_KEY
    private val _lineListFromStop = MutableLiveData<StopWithLinesAndVehicles>()
    val lineListFromStop: LiveData<StopWithLinesAndVehicles> = _lineListFromStop

    fun getLineListFromStop(stopCode : String){
        viewModelScope.launch {
            try {
                val service = RetrofitClient.instance
                val authResponse: Response<Boolean> = service.authenticate(apiKey)
                if (authResponse.isSuccessful && authResponse.body() == true) {
                    val getLines: Response<StopWithLinesAndVehicles> = service.getLinesWithVehiclesFromStop(stopCode)
                    if(getLines.isSuccessful){
                        val stopsResponse = getLines.body()
                        _lineListFromStop.value = stopsResponse!!
                    }else{
                        Log.e("API", "${getLines.errorBody()?.string()}")
                    }
                }else{
                    Log.e("API", "${authResponse.errorBody()?.string()}")
                }
            }catch (e : Exception){
                Log.e("API", "Error fetching stops", e)
            }
        }
    }

}