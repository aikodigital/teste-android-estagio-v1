package com.aiko.aikospvision.ui.screens_app.bus_lines

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.aiko.domain.usecase.GetLineBySearchTermUseCase
import com.alabia.manager.ui.state.TaskState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ViewModelBusLineImpl(
    private val getLineBySearchTermUseCase : GetLineBySearchTermUseCase
) : ViewModelBusLine()
{
    override val _state: MutableStateFlow<BusLineState> = MutableStateFlow(BusLineState())
    override val state = _state.asStateFlow()
    private val _taskState: MutableStateFlow<TaskState> = MutableStateFlow(TaskState.Idle)
    override val taskState: StateFlow<TaskState> get() = _taskState

    override fun onEvent(event: BusLineEvent) {
        when (event) {
            is BusLineEvent.InputChange -> { _state.value = _state.value.copy(inputSearch = event.input) }
            is BusLineEvent.Submit -> {getLines()}
        }
    }
    private fun getLines(){
        viewModelScope.launch {
            if(!state.value.inputSearch.isNullOrBlank()){
                val result = state.value.inputSearch?.toLong()?.let { getLineBySearchTermUseCase.execute(it) }
                if (result != null) {
                    result.handleResult({ success ->
                        _state.value = _state.value.copy(busLineList = success)
                    },{ err ->
                        Log.e(TAG, "Falha: $err")
                    })
                }
            }else{
                Log.e(TAG, "Input vazio")
            }
        }
    }
}