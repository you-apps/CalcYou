package net.youapps.calcyou.data.converters

import androidx.annotation.StringRes
import net.youapps.calcyou.R

class TemperatureConverter : UnitConverter {
    override val units: List<ConverterUnit> = listOf(
        object : ConverterUnit {
            @StringRes
            override val name: Int = R.string.celsius

            override fun convertFrom(value: Double): Double {
                return value
            }

            override fun convertTo(value: Double): Double {
                return value
            }
        },
        object : ConverterUnit {
            @StringRes
            override val name: Int = R.string.fahrenheit

            override fun convertFrom(value: Double): Double {
                return (value - 32) / 1.8
            }

            override fun convertTo(value: Double): Double {
                return (value * 1.8) + 32
            }
        },
        object : ConverterUnit {
            @StringRes
            override val name: Int = R.string.kelvin

            override fun convertFrom(value: Double): Double {
                return value - 273.15
            }

            override fun convertTo(value: Double): Double {
                return value + 273.15
            }
        },
    )
}