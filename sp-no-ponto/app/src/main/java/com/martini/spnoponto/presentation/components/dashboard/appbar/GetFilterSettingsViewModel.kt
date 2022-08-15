package com.martini.spnoponto.presentation.components.dashboard.appbar

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.spnoponto.domain.usecases.GetFilterSettingState
import com.martini.spnoponto.domain.usecases.GetFilterSettingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GetFilterSettingsViewModel @Inject constructor(
    private val getFilterSettingUseCase: GetFilterSettingUseCase
) : ViewModel() {
    private val _state = mutableStateOf<GetFilterSettingState>(GetFilterSettingState.Loading)

    val state: State<GetFilterSettingState> = _state

    init {
        listen()
        invoke()
    }

    private fun listen() {
        getFilterSettingUseCase.listen
            .onEach { _state.value = it }
            .launchIn(viewModelScope)
    }

    operator fun invoke() {
        getFilterSettingUseCase()
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }
}