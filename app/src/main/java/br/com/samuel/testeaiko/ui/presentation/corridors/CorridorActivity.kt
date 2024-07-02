package br.com.samuel.testeaiko.ui.presentation.corridors

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.samuel.testeaiko.R
import br.com.samuel.testeaiko.ui.presentation.stops.StopsActivity
import br.com.samuel.testeaiko.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CorridorActivity : ComponentActivity() {

    private val vm: CorridorVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                Content()
            }
        }
    }

    @Composable
    private fun Content() {
        Scaffold(
            topBar = { TopBar() }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                items(vm.availableCorridors) { corridor ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 8.dp,
                                bottom = 8.dp,
                                start = 16.dp,
                                end = 16.dp
                            )
                            .clickable {
                                Intent(this@CorridorActivity, StopsActivity::class.java).also {
                                    it.putExtra(StopsActivity.CORRIDOR_ID, corridor.code)
                                    it.putExtra(StopsActivity.CORRIDOR_NAME, corridor.name)
                                    startActivity(it)
                                }
                            }
                    ) {
                        Row(modifier = Modifier.padding(8.dp)) {
                            Text(text = corridor.name)
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun TopBar() {
        TopAppBar(
            title = { Text(text = stringResource(R.string.corridors)) },
            navigationIcon = {
                IconButton(onClick = { finish() }) {
                    Icon(Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = null)
                }
            }
        )
    }

}