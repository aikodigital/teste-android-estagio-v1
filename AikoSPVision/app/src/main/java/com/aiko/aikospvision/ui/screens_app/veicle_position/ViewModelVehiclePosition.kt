package com.aiko.aikospvision.ui.screens_app.veicle_position

import androidx.lifecycle.ViewModel
import com.alabia.manager.ui.state.TaskState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class ViewModelVehiclePosition : ViewModel() {
    abstract val taskState: StateFlow<TaskState>
    protected abstract val _state: MutableStateFlow<VehiclePositionState>
    open val state: StateFlow<VehiclePositionState> get() = _state.asStateFlow()
    abstract fun onEvent(event: VehiclePositionEvent)
    abstract fun saveVehicleInformation(longitude: Double?,
                                        latitude: Double?,
                                        vehicleId: Long?,
                                        vehicleOrigin: String?,
                                        vehicleDestination: String?,
                                        vehicleCompleteSign: String?,
                                        vehicleLineSense: String?,
    )
}