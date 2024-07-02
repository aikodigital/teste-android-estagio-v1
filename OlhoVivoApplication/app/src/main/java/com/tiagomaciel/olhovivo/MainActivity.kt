package com.tiagomaciel.olhovivo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tiagomaciel.olhovivo.api.ApiManager
import com.tiagomaciel.olhovivo.ui.theme.OlhoVivoApplicationTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tiagomaciel.olhovivo.api.VehiclePosition

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var items by remember {
                mutableStateOf<VehiclePosition?>(null)
            }
            OlhoVivoApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LazyColumn(Modifier.padding(innerPadding)) {
                        item {
                            ClickableButton(
                                onClick = {
                                    val apiManager = ApiManager()
                                    apiManager.authenticateAndFetchData {
                                        items = it
                                    }
                                }
                            )
                        }
                        items?.let {
                            items(it.l) { l ->
                                Text(text = l.c)
                            }
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun ClickableButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.Black
        ),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Text(text = "Clique Aqui")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    OlhoVivoApplicationTheme {
        Greeting("Android")
    }
}