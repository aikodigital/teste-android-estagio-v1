package com.example.aikodigital.gui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.aikodigital.R

@Composable
fun MenuScreen(navController: NavController){
    data class Item(var text: Int, var image: Int, var type: String)

    val items = listOf(
        Item(R.string.lines, R.drawable.bus, "lines"),
        Item(R.string.corredors, R.drawable.line, "corredors")
    )
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background)),
        verticalArrangement = Arrangement.Center
    ) {
        items(items) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(horizontal = 10.dp)
                    .clickable { navController.navigate(it.type) },
                shape = RoundedCornerShape(30.dp),
                border = BorderStroke(2.dp, colorResource(id = R.color.fonts))
                ) {
                Row(
                    modifier = Modifier
                        .background(colorResource(id = R.color.secondary))
                        .fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center

                ) {
                    Image(
                        painter = painterResource(it.image),
                        contentDescription ="",
                        colorFilter = ColorFilter.tint(colorResource(id = R.color.fonts))
                    )

                    Spacer(modifier = Modifier.width(20.dp))

                    Text(
                        text = stringResource(it.text),
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.fonts)
                    )
                }
            }
            Spacer(Modifier.height(10.dp))
        }

    }
}