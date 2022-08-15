package com.martini.spnoponto.presentation.components.dashboard.line

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.spnoponto.R
import com.martini.spnoponto.domain.entities.lineSearch.Linha
import com.martini.spnoponto.presentation.components.globalViewModels.NavigationViewModel

@ExperimentalMaterialApi
@Composable
fun SearchResultLoaded(
    lines: List<Linha>,
    navigationViewModel: NavigationViewModel = hiltViewModel()
) {

    fun navigateToLine(linha: Linha) {
        navigationViewModel.navigateToLineDetails(linha)
    }

    if (lines.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                Icons.Filled.Category,
                contentDescription = stringResource(R.string.Search),
                modifier = Modifier.size(80.dp)
            )
            Text(stringResource(R.string.no_line_found))
        }
        return
    }

    LazyColumn {
        items(lines) { line ->
            SearchResultTile(
                linha = line,
                onClick = { navigateToLine(it) }
            )
        }
    }
}