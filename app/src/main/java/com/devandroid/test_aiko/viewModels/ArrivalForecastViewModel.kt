package com.devandroid.test_aiko.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devandroid.test_aiko.models.ArrivalForecast
import com.devandroid.test_aiko.services.RetrofitInstance
import com.devandroid.test_aiko.services.repository.LiveEyeRepository
import kotlinx.coroutines.launch

class ArrivalForecastViewModel : ViewModel() {

    private var token = "82e31af3b774a240b144f4dae7dac3393ba7a9e78320dc87b12525b72fa0a645"
    private val repository = LiveEyeRepository(RetrofitInstance.api)

    private val _arrivalForecastResponse = MutableLiveData<ArrivalForecast>()
    val arrivalForecastResponse : LiveData<ArrivalForecast> get() = _arrivalForecastResponse


    fun getArrivalForecast(stopCode:Int) {
        viewModelScope.launch {
            try {
                repository.authenticate(token)
                val response = repository.getArrivalForecast(stopCode)
                if (response.isSuccessful) {
                    _arrivalForecastResponse.postValue(response.body())
                } else {

                }
            } catch (e: Exception) {
                Log.e("Error Get","Erro to Get Arrival Forecast")
            }
        }
    }
}