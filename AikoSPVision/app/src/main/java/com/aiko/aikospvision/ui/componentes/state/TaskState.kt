package com.alabia.manager.ui.state

sealed class TaskState {
    data object Idle: TaskState()
    data object Loading: TaskState()
}