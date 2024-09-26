package com.aiko.aikospvision.ui.screens_app.bus_lines

import androidx.lifecycle.ViewModel
import com.alabia.manager.ui.state.TaskState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class ViewModelBusLine : ViewModel() {
    abstract val taskState: StateFlow<TaskState>
    protected abstract val _state: MutableStateFlow<BusLineState>
    open val state: StateFlow<BusLineState> get() = _state.asStateFlow()
    abstract fun onEvent(event: BusLineEvent)
}