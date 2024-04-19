package net.youapps.calcyou.ui

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import net.youapps.calcyou.AppNavHost
import net.youapps.calcyou.Destination
import net.youapps.calcyou.R
import net.youapps.calcyou.ui.components.NavDrawerContent
import net.youapps.calcyou.ui.components.NavRailContent

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var currentDestination by remember {
        mutableStateOf<Destination>(Destination.Calculator)
    }
    val orientation = LocalConfiguration.current.orientation
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen || (orientation == ORIENTATION_LANDSCAPE),
        drawerContent = {
            NavDrawerContent(currentDestination = currentDestination, onDestinationSelected = {
                scope.launch {
                    drawerState.close()
                }
                navController.popBackStack()
                navController.navigate(it.route)
                currentDestination = it

            })
        }
    ) {
        Scaffold(topBar = {
            if (orientation == ORIENTATION_PORTRAIT) {
                CenterAlignedTopAppBar(title = {
                    Text(
                        text = stringResource(
                            id = R.string.app_name
                        )
                    )
                }, navigationIcon = {
                    IconButton(onClick = { scope.launch { drawerState.open() } }) {
                        Icon(imageVector = Icons.Rounded.Menu, contentDescription = null)
                    }
                })
            }
        }) { paddingValues ->
            Row(
                Modifier
                    .fillMaxSize()
                    .consumeWindowInsets(paddingValues)
                    .padding(paddingValues)
            ) {
                if (orientation == ORIENTATION_LANDSCAPE) {
                    NavigationRail {
                        NavRailContent(currentDestination = currentDestination) {
                            navController.popBackStack()
                            navController.navigate(it.route)
                            currentDestination = it
                        }
                    }
                }
                AppNavHost(
                    modifier = Modifier.weight(1f),
                    navHostController = navController
                )
            }

        }
    }
}