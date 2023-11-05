package net.youapps.calcyou

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Thermostat
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import net.youapps.calcyou.ui.screens.TemperatureConverter

sealed class Destination(open val route: String) {
    object Calculator : Destination("calculator")
    sealed class Converter(
        override val route: String,
        @StringRes val resId: Int,
        val icon: ImageVector,
        val screen: @Composable () -> Unit
    ) :
        Destination(route) {
        object Temperature :
            Converter("temperature", R.string.temperature, Icons.Rounded.Thermostat,
                { TemperatureConverter() })

        companion object {
            val values: Array<Converter> = arrayOf(Temperature)
        }
    }
}