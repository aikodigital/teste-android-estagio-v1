package com.martini.spnoponto.presentation.components.lineDetails.content.banner

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.spnoponto.R
import com.martini.spnoponto.domain.usecases.LineDetailsState

@Composable
fun LineDetailsBanner(
    lineDetailsViewModel: LineDetailsViewModel = hiltViewModel()
) {
    when (val state = lineDetailsViewModel.state.value) {
        is LineDetailsState.Failure -> {
            LineDetailsFailure(stringResource(R.string.unexpected_failure))
        }
        is LineDetailsState.Loaded -> LineDetailsLoaded(linha = state.linha)
        is LineDetailsState.Initial -> LineDetailsLoading()
        is LineDetailsState.InvalidArgumentFailure -> {
            LineDetailsFailure(stringResource(R.string.failure_get_line))
        }
        is LineDetailsState.Loading -> LineDetailsLoading()
    }
}