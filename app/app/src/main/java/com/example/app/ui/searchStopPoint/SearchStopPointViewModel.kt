package com.example.app.ui.searchStopPoint

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.app.domain.usecase.GetBusByStopPointUseCase
import com.example.app.domain.usecase.GetLineUseCase
import com.example.app.domain.usecase.GetStopPointByLineUseCase
import com.example.app.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SearchStopPointViewModel @Inject constructor(
    private val getBusByStopPointUseCase: GetBusByStopPointUseCase
) : ViewModel() {
    fun getBusByStopPoint(code: String) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())
            val stopPoint = getBusByStopPointUseCase(code)
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
