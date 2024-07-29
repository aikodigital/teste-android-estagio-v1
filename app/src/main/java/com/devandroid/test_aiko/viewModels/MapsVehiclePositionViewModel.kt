package com.devandroid.test_aiko.viewModels

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devandroid.test_aiko.models.PositionLine
import com.devandroid.test_aiko.services.RetrofitInstance
import com.devandroid.test_aiko.services.repository.LiveEyeRepository
import com.devandroid.test_aiko.ui.MapsVehiclePositionsFragment
import kotlinx.coroutines.launch

class MapsVehiclePositionViewModel : ViewModel() {
    private var token = "82e31af3b774a240b144f4dae7dac3393ba7a9e78320dc87b12525b72fa0a645"
    private val repository = LiveEyeRepository(RetrofitInstance.api)

    private val _positionLineResponse = MutableLiveData<PositionLine>()
    val positionLineResponse : LiveData<PositionLine> get() = _positionLineResponse

    fun getPositionLine(lineCode : Int) {
        try{
            viewModelScope.launch {
                repository.authenticate(token)
                val response = repository.getPositionWithLines(lineCode)
                if(response.isSuccessful){
                    _positionLineResponse.postValue(response.body())
                }
            }
        }  catch (e: Exception) {
            Log.e("Error Get","Erro to Get Stop Points")
        }
    }
}