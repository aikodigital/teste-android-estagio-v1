package com.example.app.ui.lineBus

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.app.BuildConfig
import com.example.app.domain.usecase.GetLineByCodeUseCase
import com.example.app.domain.usecase.GetLineUseCase
import com.example.app.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class LineBusViewModel @Inject constructor(
    private val getLineByCodeUseCase: GetLineByCodeUseCase
) : ViewModel() {
    fun getLineByCode(lineCode: String) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())
            val line = getLineByCodeUseCase(lineCode)
            emit(StateView.Success(data = listOf(line)))
        } catch (ex: HttpException) {
            ex.printStackTrace()
            emit(StateView.Error(message = ex.message()))
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(StateView.Error(message = ex.message))
        }
    }
}
