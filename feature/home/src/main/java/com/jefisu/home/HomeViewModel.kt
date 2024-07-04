package com.jefisu.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.util.fastAny
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jefisu.common.asUiText
import com.jefisu.domain.BusConnectRepository
import com.jefisu.domain.model.Line
import com.jefisu.domain.util.Result
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.net.CookieManager

class HomeViewModel(
    private val repository: BusConnectRepository,
    private val cookieManager: CookieManager
) : ViewModel() {

    var state by mutableStateOf(HomeState())
        private set

    private val _navigateAction = Channel<Line>()
    val navigateAction = _navigateAction.receiveAsFlow()

    init {
        getBusLines()
    }

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.SearchBusLine -> {
                state = state.copy(searchBusNumber = action.busNumber)
            }

            is HomeAction.NavigateToBusStops -> {
                viewModelScope.launch {
                    _navigateAction.send(action.busLine)
                }
            }
        }
    }

    fun getBusLines() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val isCookieValid = cookieManager.cookieStore.cookies.fastAny {
                !it.hasExpired()
            }
            if (!isCookieValid) {
                async { repository.authenticate() }.await()
            }

            val result = repository.getVehiclePositions()
            state = when (result) {
                is Result.Success -> state.copy(
                    busLines = result.data.busLines
                        .distinctBy { it.code }
                        .sortedBy { it.code }
                )

                is Result.Error -> state.copy(
                    error = result.error.asUiText()
                )
            }
            state = state.copy(isLoading = false)
        }
    }
}