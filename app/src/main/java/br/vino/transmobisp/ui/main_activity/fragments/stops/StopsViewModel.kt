package br.vino.transmobisp.ui.main_activity.fragments.stops

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.vino.transmobisp.BuildConfig
import br.vino.transmobisp.model.Stop
import br.vino.transmobisp.service.olho_vivo.RetrofitClient
import kotlinx.coroutines.launch
import retrofit2.Response

class StopsViewModel : ViewModel() {

    private val _stops = MutableLiveData<MutableSet<Stop>>()
    val stops: LiveData<MutableSet<Stop>> = _stops
    private val apiKey = BuildConfig.OLHO_VIVO_API_KEY

    fun getStopsWithTerm(term: String) {
        viewModelScope.launch {
            try {
                val stopsTemp = mutableSetOf<Stop>()
                val service = RetrofitClient.instance
                val authResponse: Response<Boolean> = service.authenticate(apiKey)
                if (authResponse.isSuccessful && authResponse.body() == true) {
                    val getStops: Response<List<Stop>> = service.getStopWithTerm(term)
                    if (getStops.isSuccessful) {
                        val stopsResponse = getStops.body()
                        val stopsFromResponse = stopsResponse ?: emptyList()
                        if (stopsFromResponse.isNotEmpty()) {
                            for (stop in stopsFromResponse) {
                                stopsTemp.add(stop)
                            }
                        }
                    } else {
                        Log.e("API", "${getStops.errorBody()?.string()}")
                    }
                } else {
                    Log.e("API", "${authResponse.errorBody()?.string()}")
                }
                _stops.value = stopsTemp
            } catch (e: Exception) {
                Log.e("API", "Error fetching stops", e)
            }
        }
    }

}