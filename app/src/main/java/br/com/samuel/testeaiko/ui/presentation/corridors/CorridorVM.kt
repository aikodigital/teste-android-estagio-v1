package br.com.samuel.testeaiko.ui.presentation.corridors

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.samuel.testeaiko.core.application.usecases.AuthenticateAppUC
import br.com.samuel.testeaiko.core.application.usecases.SearchCorridorsUC
import br.com.samuel.testeaiko.core.domain.model.Corridor
import br.com.samuel.testeaiko.util.ResourceResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CorridorVM @Inject constructor(
    private val authenticateApp: AuthenticateAppUC,
    private val searchCorridors: SearchCorridorsUC
) : ViewModel() {

    val availableCorridors = mutableStateListOf<Corridor>()

    private var authenticationRetries = 0

    init {
        search()
    }

    private fun search() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = searchCorridors()) {
                is ResourceResult.Error -> {
                    if (result.error == "UNAUTHORIZED") {
                        authenticationRetries++
                        if (authenticate() && authenticationRetries <= 1) {
                            search()
                        }
                    }
                }

                is ResourceResult.Success -> {
                    viewModelScope.launch(Dispatchers.Main) {
                        availableCorridors.addAll(result.data.orEmpty())
                    }
                }
            }
        }
    }

    private suspend fun authenticate(): Boolean {
        return when (authenticateApp()) {
            is ResourceResult.Error -> {
                false
            }

            is ResourceResult.Success -> {
                true
            }
        }
    }

}