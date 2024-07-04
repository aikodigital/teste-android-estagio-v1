package com.jefisu.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jefisu.common.network.ConnectivityObserver
import com.jefisu.home.components.BusDirectionsList
import com.jefisu.home.components.SearchTextField
import com.jefisu.ui.components.StandardScreen
import com.jefisu.ui.theme.AppTheme
import com.jefisu.ui.theme.BackgroundPrimaryLight
import com.jefisu.ui.theme.PrimaryDark
import com.jefisu.ui.theme.TextPrimary
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import com.jefisu.common.R as ComoonRes

@Composable
fun HomeScreen(
    state: HomeState,
    onAction: (HomeAction) -> Unit
) {
    val status by koinInject<ConnectivityObserver>()
        .observe()
        .collectAsStateWithLifecycle(
            initialValue = ConnectivityObserver.Status.Unavailable
        )
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .fillMaxHeight()
                    .background(BackgroundPrimaryLight)
                    .systemBarsPadding()
                    .padding(AppTheme.spacing.large)
            ) {
                Image(
                    painter = painterResource(id = ComoonRes.drawable.ic_bus_marker),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.height(AppTheme.spacing.extraMedium))
                Text(
                    text = "BusConnect",
                    color = PrimaryDark,
                    fontWeight = FontWeight.Medium,
                    style = AppTheme.typography.header1
                )
                Spacer(modifier = Modifier.weight(1f))
                HorizontalDivider(
                    color = TextPrimary,
                )
                Spacer(modifier = Modifier.height(AppTheme.spacing.small))
                Text(
                    text = stringResource(R.string.version, "1.0"),
                    color = PrimaryDark,
                    modifier = Modifier.padding(bottom = AppTheme.spacing.medium),
                    style = AppTheme.typography.header2,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    ) {
        StandardScreen(
            error = state.error,
            isLoading = state.isLoading,
            isOffline = status != ConnectivityObserver.Status.Available,
            fixedContent = {
                HomeTopBar(
                    state = state,
                    onAction = onAction,
                    onMenuClick = {
                        scope.launch {
                            if (drawerState.isClosed) {
                                drawerState.open()
                            } else {
                                drawerState.close()
                            }
                        }
                    },
                    modifier = Modifier
                        .statusBarsPadding()
                        .padding(horizontal = AppTheme.spacing.medium)
                )
            },
            content = {
                Spacer(modifier = Modifier.height(AppTheme.spacing.medium))
                BusDirectionsList(
                    busLines = state.busLines,
                    searchBusNumber = state.searchBusNumber,
                    onAction = onAction
                )
            }
        )
    }

}

@Composable
private fun HomeTopBar(
    state: HomeState,
    onAction: (HomeAction) -> Unit,
    onMenuClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CompositionLocalProvider(
        LocalContentColor provides PrimaryDark
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = modifier
                .fillMaxWidth()
                .padding(top = AppTheme.spacing.medium)
        ) {
            IconButton(
                onClick = onMenuClick,
                modifier = Modifier.offset(x = (-4).dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Menu,
                    contentDescription = "Menu",
                    modifier = Modifier.size(32.dp)
                )
            }
            SearchTextField(
                value = state.searchBusNumber,
                onValueChange = { onAction(HomeAction.SearchBusLine(it)) },
                placeholderText = stringResource(R.string.search_bus_number),
                modifier = Modifier.weight(1f)
            )
        }
    }
}