package net.youapps.calcyou.data.converters

import androidx.annotation.StringRes
import net.youapps.calcyou.R
import net.youapps.calcyou.data.Either

class TemperatureConverter : UnitConverter<Double> {
    override val units: List<ConverterUnit<Double>> = listOf(
        object : ConverterUnit<Double> {
            @StringRes
            override val name = Either.Left(R.string.celsius)

            override fun convertFrom(value: Double): Double {
                return value
            }

            override fun convertTo(value: Double): Double {
                return value
            }
        },
        object : ConverterUnit<Double> {
            @StringRes
            override val name = Either.Left(R.string.fahrenheit)

            override fun convertFrom(value: Double): Double {
                return (value - 32) / 1.8
            }

            override fun convertTo(value: Double): Double {
                return (value * 1.8) + 32
            }
        },
        object : ConverterUnit<Double> {
            @StringRes
            override val name = Either.Left(R.string.kelvin)

            override fun convertFrom(value: Double): Double {
                return value - 273.15
            }

            override fun convertTo(value: Double): Double {
                return value + 273.15
            }
        },
    )
}