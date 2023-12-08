package net.youapps.calcyou

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import net.youapps.calcyou.ui.CalculatorScreen
import net.youapps.calcyou.ui.screens.CharacterInputScreen
import net.youapps.calcyou.ui.screens.ConverterGridScreen
import net.youapps.calcyou.ui.screens.ConverterScreen

@Composable
fun AppNavHost(modifier: Modifier = Modifier, navHostController: NavHostController) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = Destination.Calculator.route
    ) {
        composable(route = Destination.Calculator.route) {
            CalculatorScreen()
        }

        composable(route = Destination.Converters.route) {
            ConverterGridScreen(onNavigate = {
                navHostController.navigateTo(it.route)
            })
        }

        composable(route = Destination.CharacterInput.route) {
            CharacterInputScreen()
        }

        Destination.Converter.values.forEach { converter ->
            composable(route = converter.route) {
                ConverterScreen(converter = converter.converter, converterName = converter.resId)
            }
        }
    }
}

fun NavHostController.navigateTo(route: String) = this.navigate(route) {
    launchSingleTop = true
    restoreState = true
}