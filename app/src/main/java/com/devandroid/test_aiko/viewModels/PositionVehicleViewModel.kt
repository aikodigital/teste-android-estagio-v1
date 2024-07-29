package com.devandroid.test_aiko.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devandroid.test_aiko.models.PositionResponse
import com.devandroid.test_aiko.services.RetrofitInstance
import com.devandroid.test_aiko.services.repository.LiveEyeRepository
import kotlinx.coroutines.launch

class PositionVehicleViewModel : ViewModel() {
    private var token = "82e31af3b774a240b144f4dae7dac3393ba7a9e78320dc87b12525b72fa0a645"
    private val repository = LiveEyeRepository(RetrofitInstance.api)

    private val _positionResponse = MutableLiveData<PositionResponse>()
    val positionResponse : LiveData<PositionResponse> get() = _positionResponse

    fun getPosition() {
        viewModelScope.launch {
            try {
                repository.authenticate(token)
                val response = repository.getPositionVehicle()
                if (response.isSuccessful) {
                    _positionResponse.postValue(response.body())
                } else { }
            } catch (e: Exception) {
                Log.e("Error Get","Erro to Get Position")
            }
        }
    }
}