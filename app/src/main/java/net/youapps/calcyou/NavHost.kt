package net.youapps.calcyou

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import net.youapps.calcyou.ui.CalculatorScreen

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

        Destination.Converter.values.forEach { converter ->
            composable(route = converter.route) {
                converter.screen()
            }
        }
    }
}

fun NavHostController.navigateTo(route: String) = this.navigate(route) {
    launchSingleTop = true
    restoreState = true
}