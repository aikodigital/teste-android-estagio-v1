package com.aiko.aikospvision.ui.screens_app.stops

sealed class StopsFormEvent {
    data class CurrentPlaceChanged(val currentPlaceText: String) : StopsFormEvent()
    data class DestinationChanged(val destinationText: String) : StopsFormEvent()
    data class StateChanged(val new_state: StopsState) : StopsFormEvent()
    data object Submit : StopsFormEvent()
    data object onCurrentPlaceSearch : StopsFormEvent()
    data object onDestinationSearch : StopsFormEvent()
}