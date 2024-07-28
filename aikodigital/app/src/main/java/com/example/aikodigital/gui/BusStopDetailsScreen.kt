package com.example.aikodigital.gui

import android.util.Log
import android.widget.Space
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.aikodigital.R
import com.example.aikodigital.model.MyViewModel

@Composable
fun BusStopDetailsScreen(navController: NavController, viewModel: MyViewModel){
    val previsao = viewModel.previsaoParada.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background))

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.secondary))
                .height(70.dp)
                .padding(horizontal = 20.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            
            Icon(
                painter = painterResource(id = R.drawable.arrow_circle_left),
                contentDescription = "",
                modifier = Modifier.clickable {
                    navController.popBackStack()
                },
                tint = colorResource(id = R.color.fonts)
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = AbsoluteAlignment.Right
            ) {
                Text(
                    text = stringResource(id = R.string.stop) + previsao.value.p.np,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.fonts)
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = stringResource(id = R.string.update) + previsao.value.hr,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.fonts)
                )
            }
        }

        if(previsao.value.p.l == null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.background)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            )    {
                Text(
                    text = stringResource(id = R.string.busStop),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = colorResource(id = R.color.fonts)
                )
            }
        }else{
            LazyColumn {
                Log.i("teste", "${previsao}")
                items(previsao.value.p.l) { linhas ->
                    var visible by remember { mutableStateOf(false) }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 5.dp),
                        shape = RoundedCornerShape(30.dp),
                        border = BorderStroke(2.dp, colorResource(id = R.color.fonts))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                                .background(colorResource(id = R.color.secondary))
                                .padding(20.dp, 0.dp)
                                .clickable {
                                    visible = !visible
                                },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(
                            ) {

                                Text(
                                    text = linhas.c,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = colorResource(id = R.color.fonts)
                                )

                                Text(
                                    text = if (linhas.sl == 1) linhas.lt0 else linhas.lt1,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = colorResource(id = R.color.fonts)

                                )
                            }

                            Image(
                                painter = painterResource(id = R.drawable.icon_arrow),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(35.dp),
                                colorFilter = ColorFilter.tint(colorResource(id = R.color.fonts))
                            )
                        }
                        AnimatedVisibility(
                            visible = visible,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White),
                            enter = fadeIn(
                                initialAlpha = 0.8f
                            ),
                            exit = fadeOut(
                                animationSpec = tween(durationMillis = 50)
                            )
                        ) {
                            Column(

                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(colorResource(id = R.color.secondary))
                                    .padding(20.dp)
                                    .border(BorderStroke(2.dp, colorResource(id = R.color.fonts)))

                            ) {
                                linhas.vs.forEach { onibus ->

                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .border(
                                                BorderStroke(
                                                    2.dp,
                                                    colorResource(id = R.color.fonts)
                                                )
                                            )
                                            .padding(20.dp)
                                    ) {
                                        Text(
                                            text = stringResource(id = R.string.number) + ": " + onibus.p,
                                            fontWeight = FontWeight.Bold,
                                            color = colorResource(id = R.color.fonts)
                                        )
                                        Text(
                                            text = stringResource(id = R.string.accessibility) + ": " +
                                                    if (onibus.a) stringResource(id = R.string.yes) else stringResource(id = R.string.no),
                                            fontWeight = FontWeight.Bold,
                                            color = colorResource(id = R.color.fonts)
                                        )
                                        Text(
                                            text = stringResource(id = R.string.arrival) + onibus.t,
                                            fontWeight = FontWeight.Bold,
                                            color = colorResource(id = R.color.fonts)

                                        )
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }

    }
}