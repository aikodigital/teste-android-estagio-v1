package com.martini.spnoponto.presentation.components.lineDetails.appBar

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.martini.spnoponto.R
import com.martini.spnoponto.presentation.components.globalViewModels.NavigationViewModel

@Composable
fun LineDetailsAppBar(
    navigationViewModel: NavigationViewModel = hiltViewModel()
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = { navigationViewModel.goBack() }) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back)
                )
            }
        },
        title = { Text(stringResource(R.string.line_detail)) }
    )
}