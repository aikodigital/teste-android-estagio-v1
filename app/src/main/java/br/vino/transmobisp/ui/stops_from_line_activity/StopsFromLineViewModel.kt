package br.vino.transmobisp.ui.stops_from_line_activity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.vino.transmobisp.BuildConfig
import br.vino.transmobisp.model.stops_from_line.StopsFromLine
import br.vino.transmobisp.service.olho_vivo.RetrofitClient
import kotlinx.coroutines.launch
import retrofit2.Response

class StopsFromLineViewModel : ViewModel() {

    private val apiKey = BuildConfig.OLHO_VIVO_API_KEY
    private val _stopsFromLine = MutableLiveData<StopsFromLine>()
    val stopsFromLine: LiveData<StopsFromLine> = _stopsFromLine

    fun getStopsFromLine(lineCode : String){
        viewModelScope.launch {
            try {
                val service = RetrofitClient.instance
                val authResponse: Response<Boolean> = service.authenticate(apiKey)
                if (authResponse.isSuccessful && authResponse.body() == true) {
                    val getStops: Response<StopsFromLine> = service.getStopsFromLine(lineCode)
                    if(getStops.isSuccessful){
                        val stopsResponse = getStops.body()
                            _stopsFromLine.value = stopsResponse!!
                    }else{
                        Log.e("API", "${getStops.errorBody()?.string()}")
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