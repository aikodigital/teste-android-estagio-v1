package com.aiko.aikospvision.ui.screens_app.stops

import androidx.lifecycle.ViewModel
import com.alabia.manager.ui.state.TaskState
import com.alabia.manager.ui.state.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

abstract class ViewModelStops : ViewModel() {
    protected abstract val _state: MutableStateFlow<StopsState>
    open val state: StateFlow<StopsState> get() = _state.asStateFlow()
    abstract val taskState: StateFlow<TaskState>
    abstract val validationEventChannel: Channel<ValidationEvent>
    open val validationEvents: Flow<ValidationEvent>
        get() = validationEventChannel.receiveAsFlow()
    abstract val message: StateFlow<String>
    abstract fun failed(exception: Throwable?)
    abstract fun auth()
    abstract fun onEvent(event: StopsFormEvent)
    abstract fun onGetForecast(paradaId: Long)
}

class MockViewModelStops : ViewModelStops() {
    override val _state: MutableStateFlow<StopsState> = MutableStateFlow(StopsState())
    override val state = _state.asStateFlow()

    override val validationEventChannel: Channel<ValidationEvent> = Channel()
    override val message: StateFlow<String> = MutableStateFlow("")

    private val _taskState: MutableStateFlow<TaskState> = MutableStateFlow(TaskState.Idle)
    override val taskState: StateFlow<TaskState> = _taskState
    override fun onGetForecast(paradaId: Long) {
        TODO("Not yet implemented")
    }

    override fun failed(exception: Throwable?) {
        println("Failed with exception: ${exception?.message}")
    }

    override fun auth() {
        println("Fetching data...")
    }

    override fun onEvent(event: StopsFormEvent) {
        TODO("Not yet implemented")
    }
}