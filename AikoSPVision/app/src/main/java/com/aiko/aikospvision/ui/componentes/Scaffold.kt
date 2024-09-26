package com.aiko.aikospvision.ui.componentes

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aiko.aikospvision.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    showLogo: Boolean,
    titleTopBar: String,
    showButtonToReturn: Boolean,
    navigationUp: () -> Unit,
    actions: @Composable RowScope.() -> Unit
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (showLogo) {
                    ImageLogo(modifier = Modifier.size(50.dp))
                }
                MarqueeText(
                    text = titleTopBar,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                )
            }
        },
        navigationIcon = {
            if (showButtonToReturn) {
                IconButton(onClick = navigationUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        tint = Color.Cyan
                    )
                }
            }
        },
        actions = actions,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = contrastingColorBackground(MaterialTheme.colorScheme.background),
            titleContentColor = contrastingColorBackground(MaterialTheme.colorScheme.onBackground)
        )
    )
}

@Composable
private fun MarqueeText(
    text: String,
    modifier: Modifier = Modifier,
    titleTopBarColor: Color = MaterialTheme.colorScheme.onBackground,
) {
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = text, block = {
        coroutineScope.launch {
            while (isActive) {
                delay(5000)
                scrollState.animateScrollTo(
                    value = scrollState.maxValue,
                    animationSpec = tween(durationMillis = 5000, easing = LinearOutSlowInEasing)
                )
                scrollState.animateScrollTo(
                    value = 0,
                    animationSpec = tween(durationMillis = 5000, easing = LinearOutSlowInEasing)
                )
            }
        }
    })

    Text(
        text = text,
        modifier = modifier.horizontalScroll(scrollState),
        maxLines = 1,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleSmall,
        color = titleTopBarColor,
        fontWeight = FontWeight.SemiBold
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldWithSearch(
    titleTopBar: String = "",
    onEventSearch: (String) -> Unit = { null },
    textToSearch: String = "",
    titleTopBarColor: Color = MaterialTheme.colorScheme.onBackground,
    titleTopBarAligh: Alignment = Center,
    shadowBelowTopBar: Dp = 4.dp,
    showTopBar: Boolean = false,
    actions: @Composable () -> Unit = {},
    showButtonToReturn: Boolean = false,
    showLogo: Boolean = false,
    showSearchIcon: Boolean = false,
    showBottomBarNavigation: Boolean = false,
    navigationUp: NavController,
    modifier: Modifier,
    floatingActionButton: @Composable () -> Unit = {},
    bottomNavigationBar: @Composable () -> Unit = {},
    contentToUse: @Composable (PaddingValues) -> Unit = {},
) {
    val (showSearch, setShowSearch) = remember { mutableStateOf(false) }
    val (searchText, setSearchText) = remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            if (showTopBar) {
                Surface(shadowElevation = shadowBelowTopBar) {
                    TopAppBar(
                        colors = TopAppBarDefaults.largeTopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.background,
                            titleContentColor = MaterialTheme.colorScheme.surfaceVariant,
                        ),
                        title = {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    if (showLogo && !showSearch) {
                                        ImageLogo(
                                            modifier = Modifier
                                                .size(width = 50.dp, height = 50.dp)
                                                .align(Alignment.CenterVertically)
                                        )
                                    }

                                    if (!showSearch) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .weight(1f)
                                        ) {
                                            Text(
                                                text = titleTopBar,
                                                modifier = Modifier
                                                    .align(Alignment.Center),
                                                style = MaterialTheme.typography.titleSmall,
                                                color = titleTopBarColor,
                                                fontWeight = FontWeight.SemiBold
                                            )
                                        }
                                    } else {
                                        InputSearchAppBar(
                                            onEvent = onEventSearch,
                                            textValue = textToSearch
                                        )
                                    }
                                }
                            }

                        },
                        navigationIcon = {
                            if (showButtonToReturn) {
                                IconButton(onClick = {
                                    navigationUp.navigateUp()
                                }) {
                                    Icon(
                                        Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = stringResource(id = R.string.return_description)
                                    )
                                }
                            }
                        },
                        actions = {
                            if (showSearchIcon) {
                                IconButton(onClick = { setShowSearch(!showSearch) }) {
                                    Icon(
                                        imageVector =
                                        if (showSearch) Icons.Default.Close
                                        else Icons.Default.Search,
                                        contentDescription =
                                        if (showSearch) stringResource(id = R.string.close_search_description)
                                        else stringResource(id = R.string.open_search_description)
                                    )
                                }
                            }
                            actions()
                        }
                    )
                }
            }
        },
        floatingActionButton = floatingActionButton,
        bottomBar = {
            if (showBottomBarNavigation) {
                bottomNavigationBar()
            }
        },
        content = { it ->
            contentToUse(it)
        },
        modifier = modifier.shadow(4.dp)
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Scaffold(
    modifier: Modifier = Modifier,
    titleTopBar: String = "",
    topBarComponentsColor: Color = MaterialTheme.colorScheme.onBackground,
    shadowBelowTopBar: Dp = 4.dp,
    showTopBar: Boolean = false,
    showButtonToReturn: Boolean = false,
    showLogo: Boolean = false,
    snackbarHost: @Composable () -> Unit = {},
    navigationUp: NavController,
    bottomBarContent: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    contentToUse: @Composable (PaddingValues) -> Unit = {}
) {
    Scaffold(
        snackbarHost = { snackbarHost() },
        topBar = {
            if (showTopBar) {
                Surface(shadowElevation = shadowBelowTopBar) {
                    TopAppBar(
                        title = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                if (showLogo) {
                                    ImageLogo(modifier = Modifier.size(50.dp))
                                }
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(horizontal = 8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    MarqueeText(
                                        text = titleTopBar,
                                        modifier = Modifier.fillMaxWidth(),
                                        titleTopBarColor = topBarComponentsColor
                                    )
                                }
                            }
                        },
                        navigationIcon = {
                            if (showButtonToReturn) {
                                IconButton(onClick = { navigationUp.popBackStack() }) {
                                    Icon(
                                        Icons.AutoMirrored.Filled.ArrowBack,
                                        tint = topBarComponentsColor,
                                        contentDescription = null
                                    )
                                }
                            }
                        },
                        actions = actions,
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.background,
                            titleContentColor = topBarComponentsColor,
                            navigationIconContentColor = topBarComponentsColor,
                            actionIconContentColor = topBarComponentsColor
                        )
                    )
                }
            }
        },
        content = contentToUse,
        floatingActionButton = floatingActionButton,
        bottomBar = bottomBarContent,
        modifier = modifier
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Scaffold(
    modifier: Modifier = Modifier,
    titleTopBar: String = "",
    titleTopBarColor: Color = MaterialTheme.colorScheme.onBackground,
    shadowBelowTopBar: Dp = 4.dp,
    topBarComponentsColor: Color = MaterialTheme.colorScheme.onBackground,
    showTopBar: Boolean = false,
    showButtonToReturn: Boolean = false,
    showLogo: Boolean = false,
    titleTopBarAligh: Alignment = Center,
    navigationUp: NavController,
    showBottomBarNavigation: Boolean = false,
    bottomNavigationBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    contentToUse: @Composable (PaddingValues) -> Unit = {},
    actions: @Composable RowScope.(Color) -> Unit = {},
) {
    Scaffold(
        topBar = {
            if (showTopBar) {
                Surface(shadowElevation = shadowBelowTopBar) {
                    TopAppBar(
                        colors = TopAppBarDefaults.largeTopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.background,
                            titleContentColor = topBarComponentsColor,
                        ),
                        title = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (showButtonToReturn) {
                                    IconButton(onClick = { navigationUp.popBackStack() }) {
                                        Icon(
                                            Icons.AutoMirrored.Filled.ArrowBack,
                                            tint = topBarComponentsColor,
                                            contentDescription = null
                                        )
                                    }
                                }
                                if (showLogo) {
                                    ImageLogo(modifier = Modifier.size(50.dp))
                                }
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(horizontal = 8.dp),
                                    contentAlignment = Center
                                ) {
                                    MarqueeText(
                                        text = titleTopBar,
                                        modifier = Modifier.fillMaxWidth(),
                                        titleTopBarColor = topBarComponentsColor
                                    )
                                }
                                actions(contrastingColorBackground(topBarComponentsColor))
                            }
                        }
                    )
                }
            }
        },
        content = { paddingValues -> contentToUse(paddingValues) },
        floatingActionButton = floatingActionButton,
        modifier = modifier,
        bottomBar = {
            if (showBottomBarNavigation) {
                bottomNavigationBar()
            } else Box(modifier = Modifier.padding(0.dp))
        }

    )
}