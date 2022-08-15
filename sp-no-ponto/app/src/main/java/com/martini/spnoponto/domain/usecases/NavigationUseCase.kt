package com.martini.spnoponto.domain.usecases


import com.martini.spnoponto.domain.entities.json.JsonConverterLinha
import com.martini.spnoponto.domain.entities.lineSearch.Linha
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow

class NavigationUseCase(
    private val jsonConverterLinha: JsonConverterLinha
) {
    val listen = MutableSharedFlow<NavigationAction>(0)

    fun navigateToLineDetails(line: Linha) = flow {
        try {
            val linha = jsonConverterLinha.toString(line)
            emit(NavigationAction.NavigateToLineDetails(linha))
            listen.emit(NavigationAction.NavigateToLineDetails(linha))
        } catch (e: Exception) {
            emit(NavigationAction.Failure)
            listen.emit(NavigationAction.Failure)
        }
    }

    fun goBack() = flow {
        try {
            emit(NavigationAction.GoBack)
            listen.emit(NavigationAction.GoBack)
        } catch (e: Exception) {
            emit(NavigationAction.Failure)
            listen.emit(NavigationAction.Failure)
        }
    }
}

sealed class NavigationAction {

    class NavigateToLineDetails(
        val line: String
    ) : NavigationAction()

    object GoBack : NavigationAction()
    object Failure: NavigationAction()
}