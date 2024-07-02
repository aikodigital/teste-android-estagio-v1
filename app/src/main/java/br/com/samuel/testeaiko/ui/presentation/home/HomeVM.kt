package br.com.samuel.testeaiko.ui.presentation.home

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.com.samuel.testeaiko.App
import br.com.samuel.testeaiko.R
import br.com.samuel.testeaiko.core.application.usecases.AuthenticateAppUC
import br.com.samuel.testeaiko.core.application.usecases.SearchLineByDirectionUC
import br.com.samuel.testeaiko.core.domain.enums.BusLineDirections
import br.com.samuel.testeaiko.core.domain.model.BusLine
import br.com.samuel.testeaiko.util.ResourceResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(
    application: Application,
    private val authenticateApp: AuthenticateAppUC,
    private val searchLine: SearchLineByDirectionUC
) : AndroidViewModel(application) {

    val availableLines = mutableStateListOf<BusLine>()

    val searchQuery = mutableStateOf("")
    val searchLineFilter = mutableStateOf(BusLineDirections.PRINCIPAL_TO_SECONDARY)

    val errorMessage = mutableStateOf<String?>(null)

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _loadingMessage = mutableStateOf("")
    val loadingMessage: State<String> = _loadingMessage

    private var authenticationRetries = 0

    init {
        viewModelScope.launch(Dispatchers.IO) {
            authenticate()
        }
    }

    private suspend fun authenticate(): Boolean {
        viewModelScope.launch(Dispatchers.Main) {
            _isLoading.value = true
            _loadingMessage.value = getApplication<App>().getString(R.string.message_authenticating)
        }

        return when (authenticateApp()) {
            is ResourceResult.Error -> {
                viewModelScope.launch(Dispatchers.Main) {
                    _isLoading.value = false
                    _loadingMessage.value = ""
                    errorMessage.value =
                        getApplication<App>().getString(R.string.error_authentication_failed)
                }
                false
            }

            is ResourceResult.Success -> {
                viewModelScope.launch(Dispatchers.Main) {
                    _isLoading.value = false
                    _loadingMessage.value = ""
                }
                true
            }
        }
    }

    fun search() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = searchLine(searchQuery.value, searchLineFilter.value)) {
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
                        availableLines.clear()
                        availableLines.addAll(result.data.orEmpty())
                    }
                }
            }
        }
    }

}