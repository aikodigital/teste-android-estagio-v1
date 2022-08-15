package com.martini.spnoponto.presentation.components.dashboard.line

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.spnoponto.domain.entities.lineSearch.SearchLineParams
import com.martini.spnoponto.domain.usecases.SearchLineState
import com.martini.spnoponto.domain.usecases.SearchLineUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SearchLineViewModel @Inject constructor(
    private val searchLineUseCase: SearchLineUseCase
) : ViewModel() {
    private val _state = mutableStateOf<SearchLineState>(SearchLineState.Initial)

    val state: State<SearchLineState> = _state

    operator fun invoke(params: SearchLineParams) {
        searchLineUseCase(params)
            .flowOn(Dispatchers.IO)
            .onEach { _state.value = it }
            .launchIn(viewModelScope)
    }
}