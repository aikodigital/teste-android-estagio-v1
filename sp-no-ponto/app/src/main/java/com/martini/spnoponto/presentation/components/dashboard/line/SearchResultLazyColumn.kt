package com.martini.spnoponto.presentation.components.dashboard.line

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.spnoponto.R
import com.martini.spnoponto.domain.usecases.SearchLineState

@ExperimentalMaterialApi
@Composable
fun SearchResultList(
    searchLineViewModel: SearchLineViewModel = hiltViewModel()
) {
    when(val state = searchLineViewModel.state.value) {
        is SearchLineState.Loading -> SearchResultLoading()
        is SearchLineState.Loaded -> SearchResultLoaded(lines = state.lines)
        is SearchLineState.TimeoutFailure -> {
            SearchResultFailure(stringResource(R.string.connection_unavailable))
        }
        is SearchLineState.ServerFailure -> {
            SearchResultFailure(stringResource(R.string.server_failure))
        }
        is SearchLineState.Failure -> {
            SearchResultFailure(stringResource(R.string.unexpected_failure))
        }
        else -> SearchLineInitial()
    }
}