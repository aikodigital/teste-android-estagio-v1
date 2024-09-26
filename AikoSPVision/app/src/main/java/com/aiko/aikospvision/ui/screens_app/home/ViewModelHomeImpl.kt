package com.aiko.aikospvision.ui.screens_app.home

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.aiko.domain.usecase.GetStopsBySearchTermUseCase
import com.aiko.domain.usecase.PostAuthUseCase
import com.alabia.manager.ui.state.TaskState
import com.alabia.manager.ui.state.ValidationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ViewModelHomeImpl(
    val getStopsBySearchTermUseCase: GetStopsBySearchTermUseCase,
    val postAuthUseCase: PostAuthUseCase,
) : ViewModelHome()
{
    override var _state: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
    override val state = _state.asStateFlow()

    private val _taskState: MutableStateFlow<TaskState> = MutableStateFlow(TaskState.Idle)
    override val taskState: StateFlow<TaskState> get() = _taskState

    init {
        auth()
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

    private fun change(searchText: String? = null) {
        if (searchText != null) {
            _state.value = state.value.copy(searchText = searchText)
        }
    }

    override fun onEvent(event: HomeFormEvent) {
        when (event) {
            is HomeFormEvent.SearchChanged -> change(searchText = event.searchText)
            is HomeFormEvent.Submit -> onSubmit()
        }
    }

    override fun failed(exception: Throwable?) {
        updateMessage(exception?.message ?: "Unknown error occurred!")
        viewModelScope.launch { validationEventChannel.send(ValidationEvent.Failed) }
    }

    private fun onSubmit() {
        _taskState.value = TaskState.Loading
        viewModelScope.launch {
            getStopsBySearchTermUseCase.execute(state.value.searchText).handleResult({
                _state.value = _state.value.copy(listStopped = it)
            }, {
                Log.e(TAG, "FALHA onSubmit: $it")
            })
        }
        _taskState.value = TaskState.Idle
    }
}