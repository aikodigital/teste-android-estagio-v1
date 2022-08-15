package com.martini.spnoponto.presentation.components.lineDetails.content.maps

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.spnoponto.constants.Constants
import com.martini.spnoponto.domain.entities.json.JsonConverterLinha
import com.martini.spnoponto.domain.entities.linePosition.LinePositionParams
import com.martini.spnoponto.domain.entities.lineSearch.Linha
import com.martini.spnoponto.domain.usecases.GetLinePositionState
import com.martini.spnoponto.domain.usecases.GetLinePositionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.time.Duration
import javax.inject.Inject

@HiltViewModel
class GetLinePositionViewModel @Inject constructor(
    private val getLinePositionUseCase: GetLinePositionUseCase,
    private val jsonConverterLinha: JsonConverterLinha,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf<GetLinePositionState>(GetLinePositionState.Initial)

    val state: State<GetLinePositionState> = _state

    init {
        invoke()
        updateAutomatically()
    }

    ///Atualiza automaticamente os dados
    ///de 30 em 30 segundos, usando o escopo
    ///do ViewModel atual, enquanto está ativo
    private fun updateAutomatically() {
        viewModelScope.launch {
            while (isActive) {
                delay(Duration.ofSeconds(30).toMillis())
                invoke()
            }
        }
    }

    operator fun invoke() {
        //A classe Linha foi convertida em String para ser passada
        //a uma outra screen. Aqui, converte-se de String para Linha
        val lineString: String? = savedStateHandle[Constants.LINE_DETAILS_NAV_ARG]
        lineString?.let {
            val line: Linha = jsonConverterLinha.fromString(it)
            val params = LinePositionParams(line.code)
            getLinePositionUseCase(params)
                .flowOn(Dispatchers.IO)
                .onEach { flow ->
                    _state.value = flow
                }
                .launchIn(viewModelScope)
        }
    }
}