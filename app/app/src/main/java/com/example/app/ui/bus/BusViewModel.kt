package com.example.app.ui.bus

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.app.domain.usecase.GetLineUseCase
import com.example.app.domain.usecase.GetStopPointByLineUseCase
import com.example.app.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class BusViewModel @Inject constructor(
    private val getLineUseCase: GetLineUseCase,
    private val getStopPointByLineUseCase: GetStopPointByLineUseCase
) : ViewModel() {
    fun getLines() = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())
            val lines = getLineUseCase()
            emit(StateView.Success(data = listOf(lines)))
        } catch (ex: HttpException) {
            ex.printStackTrace()
            emit(StateView.Error(message = ex.message()))
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(StateView.Error(message = ex.message))
        }
    }

    fun getStopPointByLine(code: String = "") = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())
            val stopPoint = getStopPointByLineUseCase(code)
            emit(StateView.Success(data = listOf(stopPoint)))
        } catch (ex: HttpException) {
            ex.printStackTrace()
            emit(StateView.Error(message = ex.message()))
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(StateView.Error(message = ex.message))
        }
    }
}
