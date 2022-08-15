package com.martini.spnoponto.presentation.components.globalViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martini.spnoponto.domain.entities.lineSearch.Linha
import com.martini.spnoponto.domain.usecases.NavigationAction
import com.martini.spnoponto.domain.usecases.NavigationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
    private val navigationUseCase: NavigationUseCase
) : ViewModel() {
    fun listen(): MutableSharedFlow<NavigationAction> {
        return  navigationUseCase.listen
    }

    fun navigateToLineDetails(line: Linha) {
        navigationUseCase.navigateToLineDetails(line)
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    fun goBack() {
        navigationUseCase.goBack()
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }
}