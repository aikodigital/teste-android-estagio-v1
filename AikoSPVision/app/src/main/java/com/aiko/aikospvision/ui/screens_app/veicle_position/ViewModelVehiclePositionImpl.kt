package com.aiko.aikospvision.ui.screens_app.veicle_position

import com.alabia.manager.ui.state.TaskState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ViewModelVehiclePositionImpl : ViewModelVehiclePosition() {
    override val _state: MutableStateFlow<VehiclePositionState> = MutableStateFlow(VehiclePositionState())
    override val state = _state.asStateFlow()
    private val _taskState: MutableStateFlow<TaskState> = MutableStateFlow(TaskState.Idle)
    override val taskState: StateFlow<TaskState> get() = _taskState

    override fun onEvent(event: VehiclePositionEvent) {
        when (event) {
            is VehiclePositionEvent.SaveVehicleInformationEvent -> saveVehicleInformation(
                latitude = event.latitude,
                longitude = event.longitude,
                vehicleId = event.vehicleId,
                vehicleOrigin = event.vehicleOrigin,
                vehicleDestination = event.vehicleDestination,
                vehicleCompleteSign = event.vehicleCompleteSign,
                vehicleLineSense = event.vehicleLineSense
            )
        }
    }

    override fun saveVehicleInformation(
        longitude: Double?,
        latitude: Double?,
        vehicleId: Long?,
        vehicleOrigin: String?,
        vehicleDestination: String?,
        vehicleCompleteSign: String?,
        vehicleLineSense: String?
    ) {
        _state.value = state.value.copy(
            longitude = longitude,
            latitude = latitude,
            vehicleId = vehicleId,
            vehicleOrigin = vehicleOrigin,
            vehicleDestination = vehicleDestination,
            vehicleCompleteSign = vehicleCompleteSign,
            vehicleLineSense = vehicleLineSense
        )
    }
}