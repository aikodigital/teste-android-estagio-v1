package com.alabia.manager.ui.state

sealed class ValidationEvent {
    data object Success : ValidationEvent()
    data object Failed : ValidationEvent()
}