package net.youapps.calcyou

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Carpenter
import androidx.compose.material.icons.rounded.Coffee
import androidx.compose.material.icons.rounded.GraphicEq
import androidx.compose.material.icons.rounded.Scale
import androidx.compose.material.icons.rounded.SdStorage
import androidx.compose.material.icons.rounded.Speed
import androidx.compose.material.icons.rounded.Square
import androidx.compose.material.icons.rounded.Thermostat
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import net.youapps.calcyou.data.converters.AreaConverter
import net.youapps.calcyou.data.converters.DigitalStorageConverter
import net.youapps.calcyou.data.converters.FrequencyConverter
import net.youapps.calcyou.data.converters.LengthConverter
import net.youapps.calcyou.data.converters.MassConverter
import net.youapps.calcyou.data.converters.SpeedConverter
import net.youapps.calcyou.data.converters.TemperatureConverter
import net.youapps.calcyou.data.converters.VolumeConverter
import net.youapps.calcyou.ui.screens.ConverterScreen

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
                { ConverterScreen(converter = TemperatureConverter()) })

        object Area :
            Converter("area", R.string.area, Icons.Rounded.Square,
                { ConverterScreen(converter = AreaConverter()) })

        object DigitalStorage :
            Converter("digital_storage", R.string.digital_storage, Icons.Rounded.SdStorage,
                { ConverterScreen(converter = DigitalStorageConverter()) })

        object Frequency :
            Converter("frequency", R.string.frequency, Icons.Rounded.GraphicEq,
                { ConverterScreen(converter = FrequencyConverter()) })

        object Length :
            Converter("length", R.string.length, Icons.Rounded.Carpenter,
                { ConverterScreen(converter = LengthConverter()) })

        object Mass :
            Converter("mass", R.string.mass, Icons.Rounded.Scale,
                { ConverterScreen(converter = MassConverter()) })

        object Speed :
            Converter("speed", R.string.speed, Icons.Rounded.Speed,
                { ConverterScreen(converter = SpeedConverter()) })

        object Volume :
            Converter("volume", R.string.volume, Icons.Rounded.Coffee,
                { ConverterScreen(converter = VolumeConverter()) })

        companion object {
            val values: Array<Converter> =
                arrayOf(Temperature, Area, DigitalStorage, Frequency, Length, Mass, Speed, Volume)
        }
    }
}