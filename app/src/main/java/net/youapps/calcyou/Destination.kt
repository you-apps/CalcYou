package net.youapps.calcyou

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Carpenter
import androidx.compose.material.icons.rounded.Coffee
import androidx.compose.material.icons.rounded.Compress
import androidx.compose.material.icons.rounded.DoubleArrow
import androidx.compose.material.icons.rounded.ElectricBolt
import androidx.compose.material.icons.rounded.GraphicEq
import androidx.compose.material.icons.rounded.HourglassTop
import androidx.compose.material.icons.rounded.InvertColors
import androidx.compose.material.icons.rounded.Lightbulb
import androidx.compose.material.icons.rounded.LocalGasStation
import androidx.compose.material.icons.rounded.Rotate90DegreesCw
import androidx.compose.material.icons.rounded.Scale
import androidx.compose.material.icons.rounded.SdStorage
import androidx.compose.material.icons.rounded.Speed
import androidx.compose.material.icons.rounded.Square
import androidx.compose.material.icons.rounded.TextRotationAngleup
import androidx.compose.material.icons.rounded.Thermostat
import androidx.compose.material.icons.rounded.WaterDrop
import androidx.compose.material.icons.rounded.WbSunny
import androidx.compose.ui.graphics.vector.ImageVector
import net.youapps.calcyou.data.converters.AngleConverter
import net.youapps.calcyou.data.converters.AreaConverter
import net.youapps.calcyou.data.converters.DensityConverter
import net.youapps.calcyou.data.converters.DigitalStorageConverter
import net.youapps.calcyou.data.converters.EnergyConverter
import net.youapps.calcyou.data.converters.ForceConverter
import net.youapps.calcyou.data.converters.FrequencyConverter
import net.youapps.calcyou.data.converters.FuelConverter
import net.youapps.calcyou.data.converters.LengthConverter
import net.youapps.calcyou.data.converters.LightConverter
import net.youapps.calcyou.data.converters.MassConverter
import net.youapps.calcyou.data.converters.PowerConverter
import net.youapps.calcyou.data.converters.PressureConverter
import net.youapps.calcyou.data.converters.SpeedConverter
import net.youapps.calcyou.data.converters.TemperatureConverter
import net.youapps.calcyou.data.converters.TimeConverter
import net.youapps.calcyou.data.converters.TorqueConverter
import net.youapps.calcyou.data.converters.UnitConverter
import net.youapps.calcyou.data.converters.ViscosityConverter
import net.youapps.calcyou.data.converters.VolumeConverter

sealed class Destination(open val route: String) {
    object Calculator : Destination("calculator")

    object Converters : Destination("converters")
    object CharacterInput : Destination("character_input")
    sealed class Converter(
        override val route: String,
        @StringRes val resId: Int,
        val icon: ImageVector,
        val converter: UnitConverter
    ) :
        Destination(route) {
        object Temperature :
            Converter(
                "temperature", R.string.temperature, Icons.Rounded.Thermostat,
                TemperatureConverter()
            )

        object Area :
            Converter(
                "area", R.string.area, Icons.Rounded.Square,
                AreaConverter()
            )

        object DigitalStorage :
            Converter(
                "digital_storage", R.string.digital_storage, Icons.Rounded.SdStorage,
                DigitalStorageConverter()
            )

        object Frequency :
            Converter(
                "frequency", R.string.frequency, Icons.Rounded.GraphicEq,
                FrequencyConverter()
            )

        object Length :
            Converter(
                "length", R.string.length, Icons.Rounded.Carpenter,
                LengthConverter()
            )

        object Mass :
            Converter(
                "mass", R.string.mass, Icons.Rounded.Scale,
                MassConverter()
            )

        object Speed :
            Converter(
                "speed", R.string.speed, Icons.Rounded.Speed,
                SpeedConverter()
            )

        object Volume :
            Converter(
                "volume", R.string.volume, Icons.Rounded.Coffee,
                VolumeConverter()
            )

        object Angle :
            Converter(
                "angle", R.string.angle, Icons.Rounded.TextRotationAngleup,
                AngleConverter()
            )

        object Power :
            Converter(
                "power", R.string.power, Icons.Rounded.Lightbulb,
                PowerConverter()
            )

        object Viscosity :
            Converter(
                "viscosity", R.string.viscosity, Icons.Rounded.InvertColors,
                ViscosityConverter()
            )

        object Force :
            Converter(
                "force", R.string.force, Icons.Rounded.DoubleArrow,
                ForceConverter()
            )

        object Energy :
            Converter(
                "energy", R.string.energy, Icons.Rounded.ElectricBolt,
                EnergyConverter()
            )

        object Torque :
            Converter(
                "torque", R.string.torque, Icons.Rounded.Rotate90DegreesCw,
                TorqueConverter()
            )

        object Density :
            Converter(
                "density", R.string.density, Icons.Rounded.WaterDrop,
                DensityConverter()
            )

        object Fuel :
            Converter(
                "fuel", R.string.fuel, Icons.Rounded.LocalGasStation,
                FuelConverter()
            )

        object Time :
            Converter(
                "time", R.string.time, Icons.Rounded.HourglassTop,
                TimeConverter()
            )

        object Pressure :
            Converter(
                "pressure", R.string.pressure, Icons.Rounded.Compress,
                PressureConverter()
            )

        object Light :
            Converter(
                "light", R.string.light, Icons.Rounded.WbSunny,
                LightConverter()
            )

        companion object {
            val values: Array<Converter> =
                arrayOf(
                    Temperature,
                    Area,
                    DigitalStorage,
                    Frequency,
                    Length,
                    Mass,
                    Speed,
                    Volume,
                    Angle,
                    Power,
                    Viscosity,
                    Force,
                    Energy,
                    Torque,
                    Density,
                    Fuel,
                    Time,
                    Pressure,
                    Light
                )
        }
    }
}