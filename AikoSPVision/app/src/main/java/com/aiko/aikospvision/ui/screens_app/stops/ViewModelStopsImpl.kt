package com.aiko.aikospvision.ui.screens_app.stops

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.aiko.aikospvision.gmaps.convertPrevisaoToMapCoordinators
import com.aiko.domain.usecase.GetForecastUseCase
import com.aiko.domain.usecase.PostAuthUseCase
import com.alabia.manager.ui.state.TaskState
import com.alabia.manager.ui.state.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ViewModelStopsImpl(
    private val getForecastUseCase: GetForecastUseCase,
    private val postAuthUseCase: PostAuthUseCase,
) : ViewModelStops()
{
    override var _state: MutableStateFlow<StopsState> = MutableStateFlow(StopsState())
    override val state = _state.asStateFlow()
    private val _taskState: MutableStateFlow<TaskState> = MutableStateFlow(TaskState.Idle)
    override val taskState: StateFlow<TaskState> get() = _taskState

    init {
        auth()
    }

    override fun onGetForecast(paradaId: Long) {
        viewModelScope.launch {
            _taskState.value = TaskState.Loading
            getForecastUseCase.execute(paradaId).collect { dataResult ->
                dataResult.handleResult({ forecast ->
                    val currentForecast = _state.value.forecast
                    if (currentForecast != forecast.success.data) {
                        _state.value = _state.value.copy(forecast = forecast.success.data)
                    }
                    Log.e(TAG, "Size: ${convertPrevisaoToMapCoordinators(forecast.success.data).size}")
                    _taskState.value = TaskState.Idle
                }, { error ->
                    Log.e(TAG, "FALHA ao obter previsões: $error")
                    failed(error)
                    _taskState.value = TaskState.Idle
                })
            }
        }

    }

    override val validationEventChannel = Channel<ValidationEvent>()

    override val message: StateFlow<String> get() = _message
    private val _message = MutableStateFlow("")

    private fun updateMessage(newMessage: String) {
        _message.value = newMessage
    }

    override fun auth() {
        _taskState.value = TaskState.Loading
        viewModelScope.launch {
            val result = postAuthUseCase.execute(Unit)
            result.handleResult({
                Log.i(TAG, "Success AUTH: $it")
            }, {
                Log.e(TAG, "FALHA AUTH: $it")
            })
        }
        _taskState.value = TaskState.Idle
    }

    private fun change(
        currentPlaceText: String? = null,
        destinationText: String? = null,
    ) {
        if (currentPlaceText != null) {
            _state.value = state.value.copy(currentPlaceText = currentPlaceText)
        }
        if (destinationText != null) {
            _state.value = state.value.copy(destinationText = destinationText)
        }
    }

    override fun onEvent(event: StopsFormEvent) {
        when (event) {
            is StopsFormEvent.CurrentPlaceChanged -> change(currentPlaceText = event.currentPlaceText)
            is StopsFormEvent.DestinationChanged -> change(destinationText = event.destinationText)
            is StopsFormEvent.onDestinationSearch -> onDestinationSearch()
            is StopsFormEvent.onCurrentPlaceSearch -> onCurrentPlaceSearch()
            is StopsFormEvent.StateChanged -> {
                _state.value = event.new_state
            }

            is StopsFormEvent.Submit -> onSubmit()
        }
    }

    override fun failed(exception: Throwable?) {
        updateMessage(exception?.message ?: "Unknown error occurred!")
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Failed)
        }
    }

    private fun onCurrentPlaceSearch() {}
    private fun onDestinationSearch() {}
    private fun onSubmit() {}
}