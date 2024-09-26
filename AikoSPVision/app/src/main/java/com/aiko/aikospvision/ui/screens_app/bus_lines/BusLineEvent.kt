package com.aiko.aikospvision.ui.screens_app.bus_lines

sealed class BusLineEvent {
    data class InputChange(val input: String? = null) : BusLineEvent()
    data object Submit : BusLineEvent()
}