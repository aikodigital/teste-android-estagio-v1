package com.example.app.ui.bus

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.app.BuildConfig
import com.example.app.domain.usecase.AuthUseCase
import com.example.app.domain.usecase.GetLineUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class BusViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
) : ViewModel() {

    private val _authState = MutableLiveData<Boolean>()
    val authState: LiveData<Boolean> = _authState

    private fun authenticate(token: String) {
        viewModelScope.launch {
            val isAuthenticated = authUseCase(token)
            _authState.value = isAuthenticated
        }
    }

    init {
        authenticate(BuildConfig.OLHO_VIVO_API_KEY)
    }
}
