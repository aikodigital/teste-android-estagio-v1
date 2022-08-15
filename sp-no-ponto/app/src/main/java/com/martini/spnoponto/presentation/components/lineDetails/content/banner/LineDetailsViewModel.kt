package com.martini.spnoponto.presentation.components.lineDetails.content.banner

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.spnoponto.constants.Constants
import com.martini.spnoponto.domain.usecases.LineDetailsState
import com.martini.spnoponto.domain.usecases.LineDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LineDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val lineDetailsUseCase: LineDetailsUseCase
) : ViewModel() {

    private val _state = mutableStateOf<LineDetailsState>(LineDetailsState.Initial)

    val state: State<LineDetailsState> = _state

    init {
        val linhaString: String? = savedStateHandle[Constants.LINE_DETAILS_NAV_ARG]
        linhaString?.let {
            listen()
            invoke(it)
        }
    }

    operator fun invoke(linha: String) {
        lineDetailsUseCase(linha)
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    private fun listen() {
        lineDetailsUseCase.listen
            .onEach { _state.value = it }
            .launchIn(viewModelScope)
    }

}