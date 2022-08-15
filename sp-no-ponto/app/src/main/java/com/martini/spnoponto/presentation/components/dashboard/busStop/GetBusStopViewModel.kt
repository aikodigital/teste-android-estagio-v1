package com.martini.spnoponto.presentation.components.dashboard.busStop

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.spnoponto.domain.usecases.GetBusStopState
import com.martini.spnoponto.domain.usecases.GetBusStopUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.time.Duration
import javax.inject.Inject

@HiltViewModel
class GetBusStopViewModel @Inject constructor(
    private val getBusStopUseCase: GetBusStopUseCase
) : ViewModel() {
    private val _state = mutableStateOf<GetBusStopState>(GetBusStopState.Initial)

    val state: State<GetBusStopState> = _state

    init {
        invoke()
        updateAutomatically()
    }

    private fun updateAutomatically() {
        viewModelScope.launch {
            while (isActive) {
                delay(Duration.ofSeconds(30).toMillis())
                invoke()
            }
        }
    }

    operator fun invoke() {
        getBusStopUseCase()
            .flowOn(Dispatchers.IO)
            .onEach { _state.value = it }
            .launchIn(viewModelScope)
    }
}