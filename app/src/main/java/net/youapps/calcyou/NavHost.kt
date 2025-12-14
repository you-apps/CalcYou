package net.youapps.calcyou

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import net.youapps.calcyou.data.evaluator.MathUtil
import net.youapps.calcyou.ui.CalculatorScreen
import net.youapps.calcyou.ui.components.NumberFormatter
import net.youapps.calcyou.ui.screens.CharacterInputScreen
import net.youapps.calcyou.ui.screens.ConverterGridScreen
import net.youapps.calcyou.ui.screens.ConverterScreen
import net.youapps.calcyou.ui.screens.graphing.GraphingScreen

@Composable
fun AppNavHost(modifier: Modifier = Modifier, navHostController: NavHostController) {
    val currentConfiguration = LocalConfiguration.current
    val numberFormatter = remember{ NumberFormatter(currentConfiguration) }
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = Destination.Calculator.route
    ) {
        composable(route = Destination.Calculator.route) {
            CalculatorScreen(
                formatNumberFromString = numberFormatter::formatNumberFromString,
                userLocale = numberFormatter.locale
                )
        }

        composable(route = Destination.Converters.route) {
            ConverterGridScreen(onNavigate = {
                navHostController.navigateTo(it.route)
            })
        }

        composable(route = Destination.CharacterInput.route) {
            CharacterInputScreen()
        }

        composable(route = Destination.Graphing.route) {
            GraphingScreen()
        }

        Destination.Converter.doubleConverters.forEach { converter ->
            composable(route = converter.route) {
                ConverterScreen<Double>(
                    converter = converter.converter,
                    converterName = converter.resId,
                    keyboardType = KeyboardType.Number,
                    stringToConverterArg = { it.toDoubleOrNull() },
                    converterArgToString = { MathUtil.doubleToString(it) },
                    formatNumberFromString = numberFormatter::formatNumberFromString,
                    formatOctNumberFromString = numberFormatter::formatOctNumberFromString,
                    formatHexNumberFromString = numberFormatter::formatHexNumberFromString,
                    formatBinNumberFromString = numberFormatter::formatBinNumberFromString,
                    decimalSep = numberFormatter.decimalSep
                )
            }
        }

        Destination.Converter.stringConverters.forEach { converter ->
            composable(route = converter.route) {
                ConverterScreen<String?>(
                    converter = converter.converter,
                    converterName = converter.resId,
                    keyboardType = KeyboardType.Text,
                    stringToConverterArg = { it.ifEmpty { null } },
                    converterArgToString = { it.orEmpty() },
                    formatNumberFromString = numberFormatter::formatNumberFromString,
                    formatOctNumberFromString = numberFormatter::formatOctNumberFromString,
                    formatHexNumberFromString = numberFormatter::formatHexNumberFromString,
                    formatBinNumberFromString = numberFormatter::formatBinNumberFromString,
                    decimalSep = numberFormatter.decimalSep
                )
            }
        }
    }
}

fun NavHostController.navigateTo(route: String) = this.navigate(route) {
    launchSingleTop = true
    restoreState = true
}