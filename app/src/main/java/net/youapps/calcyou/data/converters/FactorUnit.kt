package net.youapps.calcyou.data.converters

import androidx.annotation.StringRes
import net.youapps.calcyou.data.Either

class FactorUnit(stringRes: Int, val conversionFactor: Double) : ConverterUnit<Double> {
    @StringRes override val name = Either.Left(stringRes)

    override fun convertFrom(value: Double): Double {
        return value * conversionFactor
    }

    override fun convertTo(value: Double): Double {
        return value / conversionFactor
    }
}
