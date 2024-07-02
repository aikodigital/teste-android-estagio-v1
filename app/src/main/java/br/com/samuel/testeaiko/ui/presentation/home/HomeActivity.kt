package br.com.samuel.testeaiko.ui.presentation.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.DirectionsBus
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import br.com.samuel.testeaiko.R
import br.com.samuel.testeaiko.core.domain.enums.BusLineDirections
import br.com.samuel.testeaiko.ui.components.dialogs.LoadingDialog
import br.com.samuel.testeaiko.ui.presentation.corridors.CorridorActivity
import br.com.samuel.testeaiko.ui.presentation.stops.StopsActivity
import br.com.samuel.testeaiko.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {

    private val vm: HomeVM by viewModels()

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

        val scope = rememberCoroutineScope()
        val snackbarHostState = remember { SnackbarHostState() }
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

        vm.errorMessage.value?.let { message ->
            scope.launch {
                vm.errorMessage.value = null
                snackbarHostState.showSnackbar(message)
            }
        }

        ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet {
                    NavigationDrawerItem(
                        label = { Text(text = stringResource(R.string.corridors)) },
                        selected = false,
                        onClick = {
                            startActivity(
                                Intent(
                                    this@HomeActivity,
                                    CorridorActivity::class.java
                                )
                            )
                        }
                    )
                }
            },
            drawerState = drawerState
        ) {
            Scaffold(
                topBar = {
                    TopBar(drawerState)
                },
                snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
            ) { innerPadding ->
                if (vm.availableLines.isEmpty()) {
                    NoRoutesContent(innerPadding)
                } else {
                    RoutesContent(innerPadding)
                }
            }
        }
    }

    @Composable
    private fun TopBar(drawerState: DrawerState) {
        val scope = rememberCoroutineScope()

        Column {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            scope.launch {
                                if (drawerState.isOpen) {
                                    drawerState.close()
                                } else {
                                    drawerState.open()
                                }
                            }
                        }
                    ) {
                        Icon(Icons.Default.Menu, contentDescription = null)
                    }
                }
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(bottom = 16.dp)
            ) {
                TextField(
                    value = vm.searchQuery.value,
                    onValueChange = {
                        vm.searchQuery.value = it
                        vm.search()
                    },
                    label = { Text(text = stringResource(R.string.search)) },
                    shape = CircleShape,
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    leadingIcon = {
                        Icon(Icons.Outlined.Search, contentDescription = null)
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp)
                )

                Box {
                    var expandedRouteFilterMenu by remember { mutableStateOf(false) }

                    IconButton(onClick = { expandedRouteFilterMenu = true }) {
                        Icon(
                            Icons.Filled.MoreVert,
                            contentDescription = stringResource(R.string.route_filter)
                        )
                    }

                    DropdownMenu(
                        expanded = expandedRouteFilterMenu,
                        onDismissRequest = { expandedRouteFilterMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(text = stringResource(R.string.principal_to_secondary)) },
                            onClick = {
                                vm.searchLineFilter.value =
                                    BusLineDirections.PRINCIPAL_TO_SECONDARY
                                expandedRouteFilterMenu = false
                                vm.search()
                            }
                        )

                        DropdownMenuItem(
                            text = { Text(text = stringResource(R.string.secondary_to_principal)) },
                            onClick = {
                                vm.searchLineFilter.value =
                                    BusLineDirections.SECONDARY_TO_PRINCIPAL
                                expandedRouteFilterMenu = false
                                vm.search()
                            }
                        )
                    }
                }
            }

            HorizontalDivider()
        }
    }

    @Composable
    private fun NoRoutesContent(innerPadding: PaddingValues) {
        ConstraintLayout(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            val (text, progress) = createRefs()

            Text(
                text = stringResource(R.string.message_start_search_line),
                style = TextStyle(textAlign = TextAlign.Center),
                modifier = Modifier.constrainAs(text) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )

            if (vm.isLoading.value) {
                LoadingDialog(
                    modifier = Modifier.constrainAs(progress) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                    message = vm.loadingMessage.value,
                    onDismiss = {}
                )
            }
        }
    }

    @Composable
    private fun RoutesContent(innerPadding: PaddingValues) {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            val (lines, progress) = createRefs()

            LazyColumn(
                modifier = Modifier.constrainAs(lines) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            ) {
                items(vm.availableLines) { line ->
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
                                Intent(this@HomeActivity, StopsActivity::class.java).also {
                                    it.putExtra(StopsActivity.LINE_ID, line.cl)
                                    it.putExtra(StopsActivity.LINE_NAME, line.busSign())
                                    startActivity(it)
                                }
                            }
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Icon(
                                    Icons.Outlined.DirectionsBus,
                                    contentDescription = null,
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                                Text(
                                    text = line.busSign(),
                                    style = TextStyle(fontWeight = FontWeight.Bold)
                                )
                                Icon(
                                    Icons.AutoMirrored.Outlined.ArrowForward,
                                    contentDescription = null
                                )
                                Text(text = line.destination())
                            }

                            Text(
                                text = line.routeName(),
                                style = TextStyle(fontStyle = FontStyle.Italic, fontSize = 12.sp),
                                modifier = Modifier.padding(top = 6.dp)
                            )
                        }
                    }
                }
            }

            if (vm.isLoading.value) {
                LoadingDialog(
                    modifier = Modifier.constrainAs(progress) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                    message = vm.loadingMessage.value,
                    onDismiss = {}
                )
            }
        }
    }

}